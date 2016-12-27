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
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.ForgedMethodHistory;
import org.mapstruct.ap.internal.model.source.FormattingParameters;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one iterable or array type to Stream.
 * The collection elements are mapped either by a {@link TypeConversion} or another mapping method.
 *
 * @author Filip Hrisafov
 */
public class StreamMappingMethod extends WithElementMappingMethod {

    private final Set<Type> helperImports;

    public static class Builder {

        private Method method;
        private MappingBuilderContext ctx;
        private SelectionParameters selectionParameters;
        private FormattingParameters formattingParameters;
        private NullValueMappingStrategyPrism nullValueMappingStrategy;
        private ForgedMethod forgedMethod;
        private String callingContextTargetPropertyName;

        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public Builder method(Method sourceMethod) {
            this.method = sourceMethod;
            return this;
        }

        public Builder formattingParameters(FormattingParameters formattingParameters) {
            this.formattingParameters = formattingParameters;
            return this;
        }

        public Builder selectionParameters(SelectionParameters selectionParameters) {
            this.selectionParameters = selectionParameters;
            return this;
        }

        public Builder nullValueMappingStrategy(NullValueMappingStrategyPrism nullValueMappingStrategy) {
            this.nullValueMappingStrategy = nullValueMappingStrategy;
            return this;
        }

        public Builder callingContextTargetPropertyName(String callingContextTargetPropertyName) {
            this.callingContextTargetPropertyName = callingContextTargetPropertyName;
            return this;
        }

        public StreamMappingMethod build() {
            //TODO the building of the methods is the same as the IterableMappingMethod, the only difference being
            // the static extracted getElementType and the wrapper of the assignment using Java8FunctionWrapper

            Type sourceParameterType = first( method.getSourceParameters() ).getType();
            Type resultType = method.getResultType();

            Type sourceElementType = getElementType( sourceParameterType );
            Type targetElementType = getElementType( resultType );

            String loopVariableName =
                Strings.getSaveVariableName( sourceElementType.getName(), method.getParameterNames() );

            SourceRHS sourceRHS = new SourceRHS( loopVariableName, sourceElementType, new HashSet<String>(),
                "stream element"
            );
            Assignment assignment = ctx.getMappingResolver().getTargetAssignment(
                method,
                targetElementType,
                callingContextTargetPropertyName,
                formattingParameters,
                selectionParameters,
                sourceRHS,
                false
            );

            if ( assignment == null ) {
                assignment = forgeMapping( sourceRHS, sourceElementType, targetElementType );
            }
            else {
                if ( method instanceof ForgedMethod ) {
                    ForgedMethod forgedMethod = (ForgedMethod) method;
                    forgedMethod.addThrownTypes( assignment.getThrownTypes() );
                }
            }

            assignment = new Java8FunctionWrapper( assignment );

            // mapNullToDefault
            boolean mapNullToDefault = false;
            if ( method.getMapperConfiguration() != null ) {
                mapNullToDefault = method.getMapperConfiguration().isMapToDefault( nullValueMappingStrategy );
            }

            MethodReference factoryMethod = null;
            if ( !method.isUpdateMethod() ) {
                factoryMethod = ctx.getMappingResolver().getFactoryMethod( method, method.getResultType(), null );
            }

            Set<String> existingVariables = new HashSet<String>( method.getParameterNames() );
            existingVariables.add( loopVariableName );

            List<LifecycleCallbackMethodReference> beforeMappingMethods = LifecycleCallbackFactory.beforeMappingMethods(
                method,
                selectionParameters,
                ctx,
                existingVariables
            );
            List<LifecycleCallbackMethodReference> afterMappingMethods = LifecycleCallbackFactory.afterMappingMethods(
                method,
                selectionParameters,
                ctx,
                existingVariables
            );

            Set<Type> helperImports = new HashSet<Type>();
            if ( resultType.isIterableType() ) {
                helperImports.add( ctx.getTypeFactory().getType( Collectors.class ) );
            }

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

        private Assignment forgeMapping(SourceRHS sourceRHS, Type sourceType, Type targetType) {
            ForgedMethodHistory forgedMethodHistory = null;
            if ( method instanceof ForgedMethod ) {
                forgedMethodHistory = ( (ForgedMethod) method ).getHistory();
            }
            String name = getName( sourceType, targetType );
            forgedMethod = new ForgedMethod(
                name,
                sourceType,
                targetType,
                method.getMapperConfiguration(),
                method.getExecutable(),
                method.getContextParameters(),
                forgedMethodHistory
            );

            Assignment assignment = new MethodReference(
                forgedMethod,
                null,
                ParameterBinding.fromParameters( forgedMethod.getParameters() )
            );

            assignment.setAssignment( sourceRHS );

            return assignment;
        }

        private String getName(Type sourceType, Type targetType) {
            String fromName = getName( sourceType );
            String toName = getName( targetType );
            return Strings.decapitalize( fromName + "To" + toName );
        }

        private String getName(Type type) {
            StringBuilder builder = new StringBuilder();
            for ( Type typeParam : type.getTypeParameters() ) {
                builder.append( typeParam.getIdentification() );
            }
            builder.append( type.getIdentification() );
            return builder.toString();
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
