/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.assignment.LocalVarWrapper;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.ForgedMethodHistory;
import org.mapstruct.ap.internal.model.source.FormattingParameters;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.util.Strings;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one {@code Map} type to another. Keys and
 * values are mapped either by a {@link TypeConversion} or another mapping method if required.
 *
 * @author Gunnar Morling
 */
public class MapMappingMethod extends MappingMethod {

    private final Assignment keyAssignment;
    private final Assignment valueAssignment;
    private final MethodReference factoryMethod;
    private final boolean overridden;
    private final boolean mapNullToDefault;

    public static class Builder {

        private FormattingParameters keyFormattingParameters;
        private FormattingParameters valueFormattingParameters;
        private Method method;
        private MappingBuilderContext ctx;
        private NullValueMappingStrategyPrism nullValueMappingStrategy;
        private SelectionParameters keySelectionParameters;
        private SelectionParameters valueSelectionParameters;
        private List<ForgedMethod> forgedMethods = new ArrayList<ForgedMethod>();

        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public Builder method(Method sourceMethod) {
            this.method = sourceMethod;
            return this;
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
//                if ( method instanceof ForgedMethod ) {
//                    // leave messaging to calling property mapping
//                    return null;
//                }
//                else {
//                    ctx.getMessager().printMessage( method.getExecutable(),
//                        Message.MAPMAPPING_KEY_MAPPING_NOT_FOUND );
//                }
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

//                if ( method instanceof ForgedMethod ) {
//                    // leave messaging to calling property mapping
//                    return null;
//                }
//                else {
//                    ctx.getMessager().printMessage( method.getExecutable(),
//                        Message.MAPMAPPING_VALUE_MAPPING_NOT_FOUND );
//                }
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
                keyAssignment,
                valueAssignment,
                factoryMethod,
                mapNullToDefault,
                beforeMappingMethods,
                afterMappingMethods,
                forgedMethods
            );
        }

        private Assignment forgeMapping(SourceRHS sourceRHS, Type sourceType, Type targetType) {

            String name = getName( sourceType, targetType );
            ForgedMethodHistory history = null;
            if ( method instanceof ForgedMethod ) {
                history = ( (ForgedMethod) method ).getHistory();
            }
            ForgedMethod forgedMethod = new ForgedMethod(
                name,
                sourceType,
                targetType,
                method.getMapperConfiguration(),
                method.getExecutable(),
                history
            );

            Assignment assignment = new MethodReference( forgedMethod, null, targetType );
            assignment.setAssignment( sourceRHS );

            forgedMethods.add( forgedMethod );

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

    private MapMappingMethod(Method method, Assignment keyAssignment, Assignment valueAssignment,
                             MethodReference factoryMethod, boolean mapNullToDefault,
                             List<LifecycleCallbackMethodReference> beforeMappingReferences,
                             List<LifecycleCallbackMethodReference> afterMappingReferences,
                             List<ForgedMethod> forgedMethods) {
        super( method, beforeMappingReferences, afterMappingReferences, forgedMethods );

        this.keyAssignment = keyAssignment;
        this.valueAssignment = valueAssignment;
        this.factoryMethod = factoryMethod;
        this.overridden = method.overridesMethod();
        this.mapNullToDefault = mapNullToDefault;
    }

    public Parameter getSourceParameter() {
        for ( Parameter parameter : getParameters() ) {
            if ( !parameter.isMappingTarget() ) {
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
        if ( ( factoryMethod == null ) && ( !isExistingInstanceMapping() ) ) {
            types.addAll( getReturnType().getImportTypes() );
            if ( getReturnType().getImplementationType() != null ) {
                types.addAll( getReturnType().getImplementationType().getImportTypes() );
            }
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

    public MethodReference getFactoryMethod() {
        return this.factoryMethod;
    }

    public boolean isMapNullToDefault() {
        return mapNullToDefault;
    }

    public boolean isOverridden() {
        return overridden;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( getResultType() == null ) ? 0 : getResultType().hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        MapMappingMethod other = (MapMappingMethod) obj;

        if ( !getResultType().equals( other.getResultType() ) ) {
            return false;
        }

        if ( getSourceParameters().size() != other.getSourceParameters().size() ) {
            return false;
        }

        for ( int i = 0; i < getSourceParameters().size(); i++ ) {
            if ( !getSourceParameters().get( i ).getType().equals( other.getSourceParameters().get( i ).getType() ) ) {
                return false;
            }

            List<Type> thisTypeParameters = getSourceParameters().get( i ).getType().getTypeParameters();
            List<Type> otherTypeParameters = other.getSourceParameters().get( i ).getType().getTypeParameters();

            if ( !thisTypeParameters.equals( otherTypeParameters ) ) {
                return false;
            }
        }

        return isMapNullToDefault() == other.isMapNullToDefault();
    }

}
