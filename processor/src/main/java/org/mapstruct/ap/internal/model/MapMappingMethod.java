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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mapstruct.ap.internal.model.assignment.LocalVarWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one {@code Map} type to another. Keys and
 * values are mapped either by a {@link TypeConversion} or another mapping method if required.
 *
 * @author Gunnar Morling
 */
public class MapMappingMethod extends NormalTypeMappingMethod {

    private final Assignment keyAssignment;
    private final Assignment valueAssignment;
    private IterableCreation iterableCreation;

    public static class Builder extends AbstractMappingMethodBuilder<Builder, MapMappingMethod> {

        private FormattingParameters keyFormattingParameters;
        private FormattingParameters valueFormattingParameters;
        private NullValueMappingStrategyPrism nullValueMappingStrategy;
        private SelectionParameters keySelectionParameters;
        private SelectionParameters valueSelectionParameters;

        public Builder() {
            super( Builder.class );
        }

        public Builder keySelectionParameters(SelectionParameters keySelectionParameters) {
            this.keySelectionParameters = keySelectionParameters;
            return this;
        }

        public Builder valueSelectionParameters(SelectionParameters valueSelectionParameters) {
            this.valueSelectionParameters = valueSelectionParameters;
            return this;
        }

        public Builder keyFormattingParameters(FormattingParameters keyFormattingParameters) {
            this.keyFormattingParameters = keyFormattingParameters;
            return this;
        }

        public Builder valueFormattingParameters(FormattingParameters valueFormattingParameters) {
            this.valueFormattingParameters = valueFormattingParameters;
            return this;
        }

        public Builder nullValueMappingStrategy(NullValueMappingStrategyPrism nullValueMappingStrategy) {
            this.nullValueMappingStrategy = nullValueMappingStrategy;
            return this;
        }

        public MapMappingMethod build() {

            List<Type> sourceTypeParams =
                first( method.getSourceParameters() ).getType().determineTypeArguments( Map.class );
            List<Type> resultTypeParams = method.getResultType().determineTypeArguments( Map.class );

            // find mapping method or conversion for key
            Type keySourceType = sourceTypeParams.get( 0 ).getTypeBound();
            Type keyTargetType = resultTypeParams.get( 0 ).getTypeBound();

            SourceRHS keySourceRHS = new SourceRHS( "entry.getKey()", keySourceType, new HashSet<String>(), "map key" );
            Assignment keyAssignment = ctx.getMappingResolver().getTargetAssignment(
                method,
                keyTargetType,
                null, // there is no targetPropertyName
                keyFormattingParameters,
                keySelectionParameters,
                keySourceRHS,
                false
            );

            if ( keyAssignment == null ) {
                keyAssignment = forgeMapping( keySourceRHS, keySourceType, keyTargetType );
            }


            if ( keyAssignment == null ) {
                if ( method instanceof ForgedMethod ) {
                    // leave messaging to calling property mapping
                    return null;
                }
                else {
                    reportCannotCreateMapping(
                        method,
                        String.format(
                            "%s \"%s\"",
                            keySourceRHS.getSourceErrorMessagePart(),
                            keySourceRHS.getSourceType()
                        ),
                        keySourceRHS.getSourceType(),
                        keyTargetType,
                        ""
                    );
                }
            }

            // find mapping method or conversion for value
            Type valueSourceType = sourceTypeParams.get( 1 ).getTypeBound();
            Type valueTargetType = resultTypeParams.get( 1 ).getTypeBound();

            SourceRHS valueSourceRHS = new SourceRHS( "entry.getValue()", valueSourceType, new HashSet<String>(),
                    "map value" );
            Assignment valueAssignment = ctx.getMappingResolver().getTargetAssignment(
                method,
                valueTargetType,
                null, // there is no targetPropertyName
                valueFormattingParameters,
                valueSelectionParameters,
                valueSourceRHS,
                false
            );

            if ( method instanceof ForgedMethod ) {
                ForgedMethod forgedMethod = (ForgedMethod) method;
                if ( keyAssignment != null ) {
                    forgedMethod.addThrownTypes( keyAssignment.getThrownTypes() );
                }
                if ( valueAssignment != null ) {
                    forgedMethod.addThrownTypes( valueAssignment.getThrownTypes() );
                }
            }

            if ( valueAssignment == null ) {
                valueAssignment = forgeMapping( valueSourceRHS, valueSourceType, valueTargetType );
            }

            if ( valueAssignment == null ) {
                if ( method instanceof ForgedMethod ) {
                    // leave messaging to calling property mapping
                    return null;
                }
                else {
                    reportCannotCreateMapping(
                        method,
                        String.format(
                            "%s \"%s\"",
                            valueSourceRHS.getSourceErrorMessagePart(),
                            valueSourceRHS.getSourceType()
                        ),
                        valueSourceRHS.getSourceType(),
                        valueTargetType,
                        ""
                    );
                }
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

            keyAssignment = new LocalVarWrapper( keyAssignment, method.getThrownTypes(), keyTargetType, false );
            valueAssignment = new LocalVarWrapper( valueAssignment, method.getThrownTypes(), valueTargetType, false );

            Set<String> existingVariables = new HashSet<String>( method.getParameterNames() );
            List<LifecycleCallbackMethodReference> beforeMappingMethods =
                LifecycleCallbackFactory.beforeMappingMethods( method, null, ctx, existingVariables );
            List<LifecycleCallbackMethodReference> afterMappingMethods =
                LifecycleCallbackFactory.afterMappingMethods( method, null, ctx, existingVariables );

            return new MapMappingMethod(
                method,
                existingVariables,
                keyAssignment,
                valueAssignment,
                factoryMethod,
                mapNullToDefault,
                beforeMappingMethods,
                afterMappingMethods
            );
        }

        @Override
        protected boolean shouldUsePropertyNamesInHistory() {
            return true;
        }
    }

    private MapMappingMethod(Method method, Collection<String> existingVariableNames, Assignment keyAssignment,
                             Assignment valueAssignment, MethodReference factoryMethod, boolean mapNullToDefault,
                             List<LifecycleCallbackMethodReference> beforeMappingReferences,
                             List<LifecycleCallbackMethodReference> afterMappingReferences) {
        super( method, existingVariableNames, factoryMethod, mapNullToDefault, beforeMappingReferences,
            afterMappingReferences );

        this.keyAssignment = keyAssignment;
        this.valueAssignment = valueAssignment;
    }

    public Parameter getSourceParameter() {
        for ( Parameter parameter : getParameters() ) {
            if ( !parameter.isMappingTarget() && !parameter.isMappingContext() ) {
                return parameter;
            }
        }

        throw new IllegalStateException( "Method " + this + " has no source parameter." );
    }

    public List<Type> getSourceElementTypes() {
        Type sourceParameterType = getSourceParameter().getType();
        return sourceParameterType.determineTypeArguments( Map.class );
    }

    public List<Type> getResultElementTypes() {
        return getResultType().determineTypeArguments( Map.class );
    }

    public Assignment getKeyAssignment() {
        return keyAssignment;
    }

    public Assignment getValueAssignment() {
        return valueAssignment;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();

        if ( keyAssignment != null ) {
            types.addAll( keyAssignment.getImportTypes() );
        }
        if ( valueAssignment != null ) {
            types.addAll( valueAssignment.getImportTypes() );
        }

        if ( iterableCreation != null ) {
            types.addAll( iterableCreation.getImportTypes() );
        }

        return types;
    }

    public String getKeyVariableName() {
        return Strings.getSaveVariableName(
            "key",
            getParameterNames()
        );
    }

    public String getValueVariableName() {
        return Strings.getSaveVariableName(
            "value",
            getParameterNames()
        );
    }

    public String getEntryVariableName() {
        return Strings.getSaveVariableName(
            "entry",
            getParameterNames()
        );
    }

    public IterableCreation getIterableCreation() {
        if ( iterableCreation == null ) {
            iterableCreation = IterableCreation.create( this, getSourceParameter() );
        }
        return iterableCreation;
    }
}
