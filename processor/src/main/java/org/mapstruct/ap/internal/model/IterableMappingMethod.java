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

import static org.mapstruct.ap.internal.util.Collections.first;

import java.util.Collection;
import java.util.List;

import org.mapstruct.ap.internal.model.assignment.LocalVarWrapper;
import org.mapstruct.ap.internal.model.assignment.SetterWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one iterable type to another. The collection
 * elements are mapped either by a {@link TypeConversion} or another mapping method.
 *
 * @author Gunnar Morling
 */
public class IterableMappingMethod extends ContainerMappingMethod {

    public static class Builder extends ContainerMappingMethodBuilder<Builder, IterableMappingMethod> {

        public Builder() {
            super( Builder.class, "collection element" );
        }

        @Override
        protected Type getElementType(Type parameterType) {
            return parameterType.isArrayType() ? parameterType.getComponentType() : first(
                parameterType.determineTypeArguments( Iterable.class ) ).getTypeBound();
        }

        @Override
        protected Assignment getWrapper(Assignment assignment, Method method) {
            Type resultType = method.getResultType();
            // target accessor is setter, so decorate assignment as setter
            if ( resultType.isArrayType() ) {
                return new LocalVarWrapper( assignment, method.getThrownTypes(), resultType, false );
            }
            else {
                return new SetterWrapper( assignment, method.getThrownTypes(), false );
            }
        }

        @Override
        protected IterableMappingMethod instantiateMappingMethod(Method method, Collection<String> existingVariables,
            Assignment assignment, MethodReference factoryMethod, boolean mapNullToDefault, String loopVariableName,
            List<LifecycleCallbackMethodReference> beforeMappingMethods,
            List<LifecycleCallbackMethodReference> afterMappingMethods, SelectionParameters selectionParameters) {
            return new IterableMappingMethod(
                method,
                existingVariables,
                assignment,
                factoryMethod,
                mapNullToDefault,
                loopVariableName,
                beforeMappingMethods,
                afterMappingMethods,
                selectionParameters
            );
        }
    }

    private IterableMappingMethod(Method method, Collection<String> existingVariables, Assignment parameterAssignment,
                                  MethodReference factoryMethod, boolean mapNullToDefault, String loopVariableName,
                                  List<LifecycleCallbackMethodReference> beforeMappingReferences,
                                  List<LifecycleCallbackMethodReference> afterMappingReferences,
        SelectionParameters selectionParameters) {
        super(
            method,
            existingVariables,
            parameterAssignment,
            factoryMethod,
            mapNullToDefault,
            loopVariableName,
            beforeMappingReferences,
            afterMappingReferences,
            selectionParameters
        );
    }

    public Type getSourceElementType() {
        Type sourceParameterType = getSourceParameter().getType();

        if ( sourceParameterType.isArrayType() ) {
            return sourceParameterType.getComponentType();
        }
        else {
            return first( sourceParameterType.determineTypeArguments( Iterable.class ) ).getTypeBound();
        }
    }

    @Override
    public Type getResultElementType() {
        if ( getResultType().isArrayType() ) {
            return getResultType().getComponentType();
        }
        else {
            return first( getResultType().determineTypeArguments( Iterable.class ) );
        }
    }
}
