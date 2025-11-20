/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.MethodSelectors;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionContext;

/**
 * Factory for creating lists of appropriate {@link LifecycleCallbackMethodReference}s
 *
 * @author Andreas Gudian
 */
public final class LifecycleMethodResolver {

    private LifecycleMethodResolver() {
    }

    /**
     * @param method the method to obtain the beforeMapping methods for
     * @param alternativeTarget alternative to {@link Method#getResultType()} e.g. when target is abstract
     * @param selectionParameters method selectionParameters
     * @param ctx the builder context
     * @param existingVariableNames the existing variable names in the mapping method
     * @return all applicable {@code @BeforeMapping} methods for the given method
     */
    public static List<LifecycleCallbackMethodReference> beforeMappingMethods(Method method,
                                                                              Type alternativeTarget,
                                                                              SelectionParameters selectionParameters,
                                                                              MappingBuilderContext ctx,
                                                                              Set<String> existingVariableNames) {
        return collectLifecycleCallbackMethods( method,
                        alternativeTarget,
                        selectionParameters,
                        filterBeforeMappingMethods( getAllAvailableMethods( method, ctx.getSourceModel() ) ),
                        ctx,
                        existingVariableNames );
    }

    /**
     * @param method the method to obtain the afterMapping methods for
     * @param alternativeTarget alternative to {@link Method#getResultType()} e.g. when target is abstract
     * @param selectionParameters method selectionParameters
     * @param ctx the builder context
     * @param existingVariableNames list of already used variable names
     * @return all applicable {@code @AfterMapping} methods for the given method
     */
    public static List<LifecycleCallbackMethodReference> afterMappingMethods(Method method,
                                                                             Type alternativeTarget,
                                                                             SelectionParameters selectionParameters,
                                                                             MappingBuilderContext ctx,
                                                                             Set<String> existingVariableNames) {
        return collectLifecycleCallbackMethods( method,
                        alternativeTarget,
                        selectionParameters,
                        filterAfterMappingMethods( getAllAvailableMethods( method, ctx.getSourceModel() ) ),
                        ctx,
                        existingVariableNames );
    }

    /**
     * @param method the method to obtain the beforeMapping methods for
     * @param selectionParameters method selectionParameters
     * @param ctx the builder context
     * @param existingVariableNames the existing variable names in the mapping method
     * @return all applicable {@code @BeforeMapping} methods for the given method
     */
    public static List<LifecycleCallbackMethodReference> beforeMappingMethods(Method method,
                                                                              SelectionParameters selectionParameters,
                                                                              MappingBuilderContext ctx,
                                                                              Set<String> existingVariableNames) {
        return collectLifecycleCallbackMethods( method,
            method.getResultType(),
            selectionParameters,
            filterBeforeMappingMethods( getAllAvailableMethods( method, ctx.getSourceModel() ) ),
            ctx,
            existingVariableNames );
    }

    /**
     * @param method the method to obtain the afterMapping methods for
     * @param selectionParameters method selectionParameters
     * @param ctx the builder context
     * @param existingVariableNames list of already used variable names
     * @return all applicable {@code @AfterMapping} methods for the given method
     */
    public static List<LifecycleCallbackMethodReference> afterMappingMethods(Method method,
                                                                             SelectionParameters selectionParameters,
                                                                             MappingBuilderContext ctx,
                                                                             Set<String> existingVariableNames) {
        return collectLifecycleCallbackMethods( method,
            method.getResultType(),
            selectionParameters,
            filterAfterMappingMethods( getAllAvailableMethods( method, ctx.getSourceModel() ) ),
            ctx,
            existingVariableNames );
    }

    private static List<SourceMethod> getAllAvailableMethods(Method method, List<SourceMethod> sourceModelMethods) {
        ParameterProvidedMethods contextProvidedMethods = method.getContextProvidedMethods();
        if ( contextProvidedMethods.isEmpty() ) {
            return sourceModelMethods;
        }

        List<SourceMethod> methodsProvidedByParams = contextProvidedMethods
            .getAllProvidedMethodsInParameterOrder( method.getContextParameters() );

        List<SourceMethod> availableMethods =
            new ArrayList<>( methodsProvidedByParams.size() + sourceModelMethods.size() );

        availableMethods.addAll( methodsProvidedByParams );
        availableMethods.addAll( sourceModelMethods );

        return availableMethods;
    }

    private static List<LifecycleCallbackMethodReference> collectLifecycleCallbackMethods(
            Method method, Type targetType, SelectionParameters selectionParameters, List<SourceMethod> callbackMethods,
            MappingBuilderContext ctx, Set<String> existingVariableNames) {

        MethodSelectors selectors =
            new MethodSelectors( ctx.getTypeUtils(), ctx.getElementUtils(), ctx.getMessager(), ctx.getOptions() );

        List<SelectedMethod<SourceMethod>> matchingMethods = selectors.getMatchingMethods(
            callbackMethods,
            SelectionContext.forLifecycleMethods( method, targetType, selectionParameters, ctx.getTypeFactory() )
        );

        return toLifecycleCallbackMethodRefs(
            method,
            matchingMethods,
            ctx,
            existingVariableNames );
    }

    private static List<LifecycleCallbackMethodReference> toLifecycleCallbackMethodRefs(Method method,
            List<SelectedMethod<SourceMethod>> candidates,
            MappingBuilderContext ctx,
            Set<String> existingVariableNames) {

        List<LifecycleCallbackMethodReference> result = new ArrayList<>();
        for ( SelectedMethod<SourceMethod> candidate : candidates ) {
            Parameter providingParameter =
                method.getContextProvidedMethods().getParameterForProvidedMethod( candidate.getMethod() );

            if ( providingParameter != null ) {
                result.add( LifecycleCallbackMethodReference.forParameterProvidedMethod(
                    candidate,
                    providingParameter,
                    method,
                    existingVariableNames ) );
            }
            else {
                MapperReference mapperReference = MapperReference.findMapperReference(
                    ctx.getMapperReferences(),
                    candidate.getMethod() );

                result.add( LifecycleCallbackMethodReference.forMethodReference(
                    candidate,
                    mapperReference,
                    method,
                    existingVariableNames ) );
            }
        }
        return result;
    }

    private static List<SourceMethod> filterBeforeMappingMethods(List<SourceMethod> methods) {
        return methods.stream()
                      .filter( SourceMethod::isBeforeMappingMethod )
                      .collect( Collectors.toList() );
    }

    private static List<SourceMethod> filterAfterMappingMethods(List<SourceMethod> methods) {
        return methods.stream()
                      .filter( SourceMethod::isAfterMappingMethod )
                      .collect( Collectors.toList() );
    }
}
