/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.mapstruct.ap.internal.gem.ConditionStrategyGem;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
import org.mapstruct.ap.internal.model.common.SourceRHS;
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

        // For a property-level @Condition, the candidate parameter bindings include every source
        // parameter of the mapping method alongside the property's source value (SourceRHS). When
        // the SourceRHS type does not match any parameter of the @Condition method but an unrelated
        // sibling source parameter happens to match by type, the type selector used to silently
        // bind that sibling and the generated check ran on the wrong value. A sibling source
        // parameter is only a valid binding when it is the root of the property's source path
        // (e.g. @Mapping(source = "rootParam.nested.value") may bind a method parameter to
        // rootParam itself), otherwise reject the match and let the default null check take over.
        // See #4037.
        SourceRHS sourceRHS = selectionParameters == null ? null : selectionParameters.getSourceRHS();
        if ( sourceRHS != null && !sourceRHS.isSourceReferenceParameter() ) {
            String rootSourceParameterName = sourceRHS.getSourceParameterName();
            matchingMethods.removeIf( selected -> bindsUnrelatedSourceParameter(
                selected.getParameterBindings(),
                rootSourceParameterName
            ) );
        }

        if ( matchingMethods.isEmpty() ) {
            return null;
        }

        if ( matchingMethods.size() > 1 ) {
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

        SelectedMethod<SourceMethod> matchingMethod = matchingMethods.get( 0 );

        MethodReference methodReference = getPresenceCheckMethodReference( method, matchingMethod, ctx );

        return new MethodReferencePresenceCheck( methodReference );

    }

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

    private static boolean bindsUnrelatedSourceParameter(List<ParameterBinding> bindings,
                                                          String rootSourceParameterName) {
        for ( ParameterBinding binding : bindings ) {
            if ( !binding.isSourceParameter() ) {
                continue;
            }
            if ( binding.isMappingTarget() || binding.isTargetType() || binding.isMappingContext()
                || binding.isSourcePropertyName() || binding.isTargetPropertyName() ) {
                continue;
            }
            if ( !Objects.equals( binding.getVariableName(), rootSourceParameterName ) ) {
                return true;
            }
        }
        return false;
    }

    private static List<SourceMethod> getAllAvailableMethods(Method method, List<SourceMethod> sourceModelMethods,
                                                             SelectionCriteria selectionCriteria) {
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
