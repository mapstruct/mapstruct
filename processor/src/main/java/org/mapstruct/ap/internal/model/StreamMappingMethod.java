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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.assignment.Java8FunctionWrapper;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one iterable or array type to Stream.
 * The collection elements are mapped either by a {@link TypeConversion} or another mapping method.
 *
 * @author Filip Hrisafov
 */
public class StreamMappingMethod extends WithElementMappingMethod {

    private final Set<Type> helperImports;

    public static class Builder extends WithElementMappingMethodBuilder<Builder, StreamMappingMethod> {

        public Builder() {
            super( Builder.class, "stream element" );
        }

        @Override
        protected Type getElementType(Type parameterType) {
            return StreamMappingMethod.getElementType( parameterType );
        }

        @Override
        protected Assignment getWrapper(Assignment assignment, Method method) {
            return new Java8FunctionWrapper( assignment );
        }

        @Override
        protected StreamMappingMethod instantiateMappingMethod(Method method, Assignment assignment,
            MethodReference factoryMethod, boolean mapNullToDefault, String loopVariableName,
            List<LifecycleCallbackMethodReference> beforeMappingMethods,
            List<LifecycleCallbackMethodReference> afterMappingMethods, SelectionParameters selectionParameters,
            ForgedMethod forgedMethod) {

            Set<Type> helperImports = new HashSet<Type>();
            if ( method.getResultType().isIterableType() ) {
                helperImports.add( ctx.getTypeFactory().getType( Collectors.class ) );
            }

            Type sourceParameterType = first( method.getSourceParameters() ).getType();
            if ( !sourceParameterType.isCollectionType() && !sourceParameterType.isArrayType() &&
                sourceParameterType.isIterableType() ) {
                helperImports.add( ctx.getTypeFactory().getType( StreamSupport.class ) );
            }

            return new StreamMappingMethod(
                method,
                assignment,
                factoryMethod,
                mapNullToDefault,
                loopVariableName,
                beforeMappingMethods,
                afterMappingMethods,
                selectionParameters,
                helperImports,
                forgedMethod
            );
        }
    }

    private StreamMappingMethod(Method method, Assignment parameterAssignment, MethodReference factoryMethod,
                                boolean mapNullToDefault, String loopVariableName,
                                List<LifecycleCallbackMethodReference> beforeMappingReferences,
                                List<LifecycleCallbackMethodReference> afterMappingReferences,
                                SelectionParameters selectionParameters, Set<Type> helperImports,
                                ForgedMethod forgedMethod) {
        super(
            method,
            parameterAssignment,
            factoryMethod,
            mapNullToDefault,
            loopVariableName,
            beforeMappingReferences,
            afterMappingReferences,
            selectionParameters,
            forgedMethod
        );
        this.helperImports = helperImports;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();

        types.addAll( helperImports );

        return types;
    }

    public Type getSourceElementType() {
        return getElementType( getSourceParameter().getType() );
    }

    public Type getResultElementType() {
        return getElementType( getResultType() );
    }

    private static Type getElementType(Type parameterType) {
        if ( parameterType.isArrayType() ) {
            return parameterType.getComponentType();
        }
        else if ( parameterType.isIterableType() ) {
            return first( parameterType.determineTypeArguments( Iterable.class ) ).getTypeBound();
        }
        else if ( parameterType.isStreamType() ) {
            return first( parameterType.determineTypeArguments( Stream.class ) ).getTypeBound();
        }

        throw new IllegalArgumentException( "Could not get the element type" );
    }
}
