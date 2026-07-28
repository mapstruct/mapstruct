/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.ap.internal.gem.ConditionStrategyGem;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
import org.mapstruct.ap.internal.model.presence.NullPresenceCheck;
import org.mapstruct.ap.internal.model.presence.OptionalPresenceCheck;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.MethodSelectors;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionContext;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.NullabilityResolver;

/**
 * Factory for creating {@link PresenceCheck}s.
 *
 * @author Filip Hrisafov
 */
public final class PresenceCheckMethodResolver {

    private PresenceCheckMethodResolver() {

    }

    public static PresenceCheck getPresenceCheck(
        Method method,
        SelectionParameters selectionParameters,
        MappingBuilderContext ctx
    ) {
        List<SelectedMethod<SourceMethod>> matchingMethods = findMatchingMethods(
            method,
            SelectionContext.forPresenceCheckMethods( method, selectionParameters, ctx.getTypeFactory() ),
            ctx
        );

        if ( matchingMethods.isEmpty() ) {
            return null;
        }

        if ( matchingMethods.size() > 1 ) {
            // When multiple condition methods match, select the most specific one
            // (the one with the most parameters that can be bound)
            SelectedMethod<SourceMethod> mostSpecificMethod = selectMostSpecificConditionMethod( matchingMethods );
            if ( mostSpecificMethod == null ) {
                // If still ambiguous, report the error
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.GENERAL_AMBIGUOUS_PRESENCE_CHECK_METHOD,
                    selectionParameters.getSourceRHS().getSourceType().describe(),
                    matchingMethods.stream()
                        .map( SelectedMethod::getMethod )
                        .map( Method::describe )
                        .collect( Collectors.joining( ", " ) )
                );
                return null;
            }
            matchingMethods = Arrays.asList( mostSpecificMethod );
        }

        SelectedMethod<SourceMethod> matchingMethod = matchingMethods.get( 0 );
        MethodReference methodReference = getPresenceCheckMethodReference( method, matchingMethod, ctx );
        return new MethodReferencePresenceCheck( methodReference );
    }

    /**
     * Creates a {@link PresenceCheck} for the given source parameter.
     *
     * @param method the method
     * @param selectionParameters the selection parameters
     * @param sourceParameter the source parameter
     * @param ctx the mapping builder context
     * @return the presence check, or null if no matching methods are found
     */
    public static PresenceCheck getPresenceCheckForSourceParameter(
        Method method,
        SelectionParameters selectionParameters,
        Parameter sourceParameter,
        MappingBuilderContext ctx
    ) {
        List<SelectedMethod<SourceMethod>> matchingMethods = findMatchingMethods(
            method,
            SelectionContext.forSourceParameterPresenceCheckMethods(
                method,
                selectionParameters,
                sourceParameter,
                ctx.getTypeFactory()
            ),
            ctx
        );

        if ( matchingMethods.isEmpty() ) {
            if ( sourceParameter.getType().isOptionalType() ) {
                return new OptionalPresenceCheck( sourceParameter.getName(), ctx.getVersionInformation() );
            }
            else if ( !sourceParameter.getType().isPrimitive() ) {
                // If the source parameter is @NonNull (JSpecify), skip the null guard entirely.
                // Resolved in the mapper's @NullMarked scope since the parameter is declared in the mapper interface.
                if ( ctx.getNullabilityInMapperScope( sourceParameter.getElement() )
                    == NullabilityResolver.Nullability.NON_NULL ) {
                    ctx.getMessager().note( 2,
                        Message.PROPERTYMAPPING_JSPECIFY_SKIP_METHOD_GUARD_NON_NULL_PARAM,
                        sourceParameter.getName()
                    );
                    return null;
                }
                return new NullPresenceCheck( sourceParameter.getName() );
            }
            return null;
        }

        if ( matchingMethods.size() > 1 ) {
            // When multiple condition methods match, select the most specific one
            // (the one with the most parameters that can be bound)
            SelectedMethod<SourceMethod> mostSpecificMethod = selectMostSpecificConditionMethod( matchingMethods );
            if ( mostSpecificMethod == null ) {
                // If still ambiguous, report the error
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.GENERAL_AMBIGUOUS_SOURCE_PARAMETER_CHECK_METHOD,
                    sourceParameter.getType().describe(),
                    matchingMethods.stream()
                        .map( SelectedMethod::getMethod )
                        .map( Method::describe )
                        .collect( Collectors.joining( ", " ) )
                );
                return null;
            }
            matchingMethods = Arrays.asList( mostSpecificMethod );
        }

        SelectedMethod<SourceMethod> matchingMethod = matchingMethods.get( 0 );
        MethodReference methodReference = getPresenceCheckMethodReference( method, matchingMethod, ctx );
        return new MethodReferencePresenceCheck( methodReference );
    }

    private static List<SelectedMethod<SourceMethod>> findMatchingMethods(
        Method method,
        SelectionContext selectionContext,
        MappingBuilderContext ctx
    ) {
        MethodSelectors selectors = new MethodSelectors(
            ctx.getTypeUtils(),
            ctx.getElementUtils(),
            ctx.getMessager(),
            null
        );

        return selectors.getMatchingMethods(
            getAllAvailableMethods( method, ctx.getSourceModel(), selectionContext.getSelectionCriteria() ),
            selectionContext
        );
    }

    /**
     * When multiple condition methods match, select the most specific one based on parameter count.
     * The method with the most parameters that can be bound is considered most specific.
     * If multiple methods have the same maximum parameter count, returns null (ambiguous).
     */
    private static SelectedMethod<SourceMethod> selectMostSpecificConditionMethod(
        List<SelectedMethod<SourceMethod>> matchingMethods) {

        if ( matchingMethods.size() <= 1 ) {
            return matchingMethods.isEmpty() ? null : matchingMethods.get( 0 );
        }

        // Find the method with the highest number of bindable parameters
        SelectedMethod<SourceMethod> mostSpecific = null;
        int maxParameterCount = -1;
        boolean hasAmbiguity = false;

        for ( SelectedMethod<SourceMethod> method : matchingMethods ) {
            List<ParameterBinding> bindings = method.getParameterBindings();
            int paramCount = bindings != null ? bindings.size() : 0;

            if ( paramCount > maxParameterCount ) {
                maxParameterCount = paramCount;
                mostSpecific = method;
                hasAmbiguity = false;
            }
            else if ( paramCount == maxParameterCount ) {
                hasAmbiguity = true;
            }
        }

        return hasAmbiguity ? null : mostSpecific;
    }

    private static MethodReference getPresenceCheckMethodReference(
        Method method,
        SelectedMethod<SourceMethod> matchingMethod,
        MappingBuilderContext ctx
    ) {

        Parameter providingParameter =
            method.getContextProvidedMethods().getParameterForProvidedMethod( matchingMethod.getMethod() );
        if ( providingParameter != null ) {
            return MethodReference.forParameterProvidedMethod(
                matchingMethod.getMethod(),
                providingParameter,
                matchingMethod.getParameterBindings()
            );
        }
        else {
            MapperReference ref = MapperReference.findMapperReference(
                ctx.getMapperReferences(),
                matchingMethod.getMethod()
            );

            return MethodReference.forMapperReference(
                matchingMethod.getMethod(),
                ref,
                matchingMethod.getParameterBindings()
            );
        }
    }

    private static List<SourceMethod> getAllAvailableMethods(
        Method method,
        List<SourceMethod> sourceModelMethods,
        SelectionCriteria selectionCriteria
    ) {
        ParameterProvidedMethods contextProvidedMethods = method.getContextProvidedMethods();
        if ( contextProvidedMethods.isEmpty() ) {
            return sourceModelMethods;
        }

        List<SourceMethod> methodsProvidedByParams = contextProvidedMethods
            .getAllProvidedMethodsInParameterOrder( method.getContextParameters() );

        List<SourceMethod> availableMethods =
            new ArrayList<>( methodsProvidedByParams.size() + sourceModelMethods.size() );

        for ( SourceMethod methodProvidedByParams : methodsProvidedByParams ) {
            if ( selectionCriteria.isPresenceCheckRequired() ) {
                // add only methods from context that do have the @Condition for properties annotation
                if ( methodProvidedByParams.getConditionOptions()
                    .isStrategyApplicable( ConditionStrategyGem.PROPERTIES ) ) {
                    availableMethods.add( methodProvidedByParams );
                }
            }
            else if ( selectionCriteria.isSourceParameterCheckRequired() ) {
                // add only methods from context that do have the @Condition for source parameters annotation
                if ( methodProvidedByParams.getConditionOptions()
                    .isStrategyApplicable( ConditionStrategyGem.SOURCE_PARAMETERS ) ) {
                    availableMethods.add( methodProvidedByParams );
                }
            }
        }
        availableMethods.addAll( sourceModelMethods );

        return availableMethods;
    }
}
