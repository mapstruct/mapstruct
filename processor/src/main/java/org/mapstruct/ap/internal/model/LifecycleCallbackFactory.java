/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.MethodSelectors;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;

/**
 * Factory for creating lists of appropriate {@link LifecycleCallbackMethodReference}s
 *
 * @author Andreas Gudian
 */
public final class LifecycleCallbackFactory {

    private LifecycleCallbackFactory() {
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
        return collectLifecycleCallbackMethods(
            method,
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
        return collectLifecycleCallbackMethods(
            method,
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
            new ArrayList<SourceMethod>( methodsProvidedByParams.size() + sourceModelMethods.size() );

        availableMethods.addAll( methodsProvidedByParams );
        availableMethods.addAll( sourceModelMethods );

        return availableMethods;
    }

    private static List<LifecycleCallbackMethodReference> collectLifecycleCallbackMethods(
            Method method, SelectionParameters selectionParameters, List<SourceMethod> callbackMethods,
            MappingBuilderContext ctx, Set<String> existingVariableNames) {

        MethodSelectors selectors =
            new MethodSelectors( ctx.getTypeUtils(), ctx.getElementUtils(), ctx.getTypeFactory() );

        Type targetType = method.getResultType();

        if ( !method.isUpdateMethod() ) {
            targetType = targetType.getEffectiveType();
        }

        List<SelectedMethod<SourceMethod>> matchingMethods = selectors.getMatchingMethods(
            method,
            callbackMethods,
            Collections.<Type> emptyList(),
            targetType,
            SelectionCriteria.forLifecycleMethods( selectionParameters ) );

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

        List<LifecycleCallbackMethodReference> result = new ArrayList<LifecycleCallbackMethodReference>();
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
                MapperReference mapperReference = findMapperReference(
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

    private static MapperReference findMapperReference(List<MapperReference> mapperReferences, SourceMethod method) {
        for ( MapperReference ref : mapperReferences ) {
            if ( ref.getType().equals( method.getDeclaringMapper() ) ) {
                ref.setUsed( ref.isUsed() || !method.isStatic() );
                ref.setTypeRequiresImport( true );
                return ref;
            }
        }
        return null;
    }

    private static List<SourceMethod> filterBeforeMappingMethods(List<SourceMethod> methods) {
        List<SourceMethod> result = new ArrayList<SourceMethod>();
        for ( SourceMethod method : methods ) {
            if ( method.isBeforeMappingMethod() ) {
                result.add( method );
            }
        }

        return result;
    }

    private static List<SourceMethod> filterAfterMappingMethods(List<SourceMethod> methods) {
        List<SourceMethod> result = new ArrayList<SourceMethod>();
        for ( SourceMethod method : methods ) {
            if ( method.isAfterMappingMethod() ) {
                result.add( method );
            }
        }

        return result;
    }
}
