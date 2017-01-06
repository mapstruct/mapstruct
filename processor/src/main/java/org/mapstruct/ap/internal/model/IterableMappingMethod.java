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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.assignment.LocalVarWrapper;
import org.mapstruct.ap.internal.model.assignment.SetterWrapper;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.ForgedMethodHistory;
import org.mapstruct.ap.internal.model.source.FormattingParameters;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.util.Strings;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one iterable type to another. The collection
 * elements are mapped either by a {@link TypeConversion} or another mapping method.
 *
 * @author Gunnar Morling
 */
public class IterableMappingMethod extends WithElementMappingMethod {

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

        public IterableMappingMethod build() {


            Type sourceParameterType = first( method.getSourceParameters() ).getType();
            Type resultType = method.getResultType();

            Type sourceElementType =
                sourceParameterType.isArrayType() ? sourceParameterType.getComponentType() : first(
                    sourceParameterType.determineTypeArguments( Iterable.class ) ).getTypeBound();
            Type targetElementType =
                resultType.isArrayType() ? resultType.getComponentType() : first(
                    resultType.determineTypeArguments( Iterable.class ) ).getTypeBound();

            String loopVariableName =
                Strings.getSaveVariableName( sourceElementType.getName(), method.getParameterNames() );

            SourceRHS sourceRHS = new SourceRHS( loopVariableName, sourceElementType, new HashSet<String>(),
                "collection element" );
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
            // target accessor is setter, so decorate assignment as setter
            if ( resultType.isArrayType() ) {
                assignment = new LocalVarWrapper( assignment, method.getThrownTypes(), resultType, false );
            }
            else {
                assignment = new SetterWrapper( assignment, method.getThrownTypes(), false );
            }

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
                existingVariables );
            List<LifecycleCallbackMethodReference> afterMappingMethods = LifecycleCallbackFactory.afterMappingMethods(
                method,
                selectionParameters,
                ctx,
                existingVariables );

            return new IterableMappingMethod(
                method,
                assignment,
                factoryMethod,
                mapNullToDefault,
                loopVariableName,
                beforeMappingMethods,
                afterMappingMethods,
                selectionParameters,
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
                ParameterBinding.fromParameters( forgedMethod.getParameters() ) );

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

    private IterableMappingMethod(Method method, Assignment parameterAssignment, MethodReference factoryMethod,
                                  boolean mapNullToDefault, String loopVariableName,
                                  List<LifecycleCallbackMethodReference> beforeMappingReferences,
                                  List<LifecycleCallbackMethodReference> afterMappingReferences,
                                  SelectionParameters selectionParameters, ForgedMethod forgedMethod) {
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

    public Type getResultElementType() {
        if ( getResultType().isArrayType() ) {
            return getResultType().getComponentType();
        }
        else {
            return first( getResultType().determineTypeArguments( Iterable.class ) );
        }
    }
}
