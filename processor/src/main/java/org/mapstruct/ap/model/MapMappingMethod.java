/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model;

import java.util.List;
import java.util.Set;

import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.model.assignment.Assignment;
import org.mapstruct.ap.model.assignment.LocalVarWrapper;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.util.Message;
import org.mapstruct.ap.util.Strings;

import static org.mapstruct.ap.util.Collections.first;

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

        private String keyDateFormat;
        private String valueDateFormat;
        private List<TypeMirror> keyQualifiers;
        private List<TypeMirror> valueQualifiers;
        private TypeMirror keyQualifyingTargetType;
        private TypeMirror valueQualifyingTargetType;
        private Method method;
        private MappingBuilderContext ctx;
        private NullValueMappingStrategyPrism nullValueMappingStrategy;

        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public Builder method(Method sourceMethod) {
            this.method = sourceMethod;
            return this;
        }

        public Builder keyDateFormat(String keyDateFormat) {
            this.keyDateFormat = keyDateFormat;
            return this;
        }

        public Builder valueDateFormat(String valueDateFormat) {
            this.valueDateFormat = valueDateFormat;
            return this;
        }

        public Builder keyQualifiers(List<TypeMirror> keyQualifiers) {
            this.keyQualifiers = keyQualifiers;
            return this;
        }

        public Builder valueQualifiers(List<TypeMirror> valueQualifiers) {
            this.valueQualifiers = valueQualifiers;
            return this;
        }

        public Builder keyQualifyingTargetType(TypeMirror keyQualifyingTargetType) {
            this.keyQualifyingTargetType = keyQualifyingTargetType;
            return this;
        }

        public Builder valueQualifyingTargetType(TypeMirror valueQualifyingTargetType) {
            this.valueQualifyingTargetType = valueQualifyingTargetType;
            return this;
        }

        public Builder nullValueMappingStrategy(NullValueMappingStrategyPrism nullValueMappingStrategy) {
            this.nullValueMappingStrategy = nullValueMappingStrategy;
            return this;
        }



        public MapMappingMethod build() {

            List<Type> sourceTypeParams = first( method.getSourceParameters() ).getType().getTypeParameters();
            List<Type> resultTypeParams = method.getResultType().getTypeParameters();

            // find mapping method or conversion for key
            Type keySourceType = sourceTypeParams.get( 0 ).getTypeBound();
            Type keyTargetType = resultTypeParams.get( 0 ).getTypeBound();

            Assignment keyAssignment = ctx.getMappingResolver().getTargetAssignment(
                method,
                "map key",
                keySourceType,
                keyTargetType,
                null, // there is no targetPropertyName
                keyDateFormat,
                keyQualifiers,
                keyQualifyingTargetType,
                "entry.getKey()",
                false
            );

            if ( keyAssignment == null ) {
                ctx.getMessager().printMessage( method.getExecutable(), Message.MAPMAPPING_KEY_MAPPING_NOT_FOUND );
            }

            // find mapping method or conversion for value
            Type valueSourceType = sourceTypeParams.get( 1 ).getTypeBound();
            Type valueTargetType = resultTypeParams.get( 1 ).getTypeBound();

            Assignment valueAssignment = ctx.getMappingResolver().getTargetAssignment(
                method,
                "map value",
                valueSourceType,
                valueTargetType,
                null, // there is no targetPropertyName
                valueDateFormat,
                valueQualifiers,
                valueQualifyingTargetType,
                "entry.getValue()",
                false
            );

            if ( valueAssignment == null ) {
                ctx.getMessager().printMessage( method.getExecutable(), Message.MAPMAPPING_VALUE_MAPPING_NOT_FOUND );
            }

            // mapNullToDefault
            boolean mapNullToDefault = false;
            if ( method.getMapperConfiguration() != null ) {
                 mapNullToDefault = method.getMapperConfiguration().isMapToDefault( nullValueMappingStrategy );
            }

            MethodReference factoryMethod =
                ctx.getMappingResolver().getFactoryMethod( method, method.getResultType(), null, null );

            keyAssignment = new LocalVarWrapper( keyAssignment, method.getThrownTypes() );
            valueAssignment = new LocalVarWrapper( valueAssignment, method.getThrownTypes() );

            return new MapMappingMethod(
                method,
                keyAssignment,
                valueAssignment,
                factoryMethod,
                mapNullToDefault
            );
        }
    }

    private MapMappingMethod(Method method, Assignment keyAssignment, Assignment valueAssignment,
                             MethodReference factoryMethod, boolean mapNullToDefault) {
        super( method );

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
            if ( !getSourceParameters().get( i ).getType().getTypeParameters().get( 0 )
                .equals( other.getSourceParameters().get( i ).getType().getTypeParameters().get( 0 ) ) ) {
                return false;
            }
        }

        return true;
    }

}
