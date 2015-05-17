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

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.model.assignment.Assignment;
import org.mapstruct.ap.model.assignment.LocalVarWrapper;
import org.mapstruct.ap.model.assignment.SetterWrapper;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.util.Message;
import org.mapstruct.ap.util.Strings;

import static org.mapstruct.ap.util.Collections.first;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one iterable type to another. The collection
 * elements are mapped either by a {@link TypeConversion} or another mapping method.
 *
 * @author Gunnar Morling
 */
public class IterableMappingMethod extends MappingMethod {

    private final Assignment elementAssignment;
    private final MethodReference factoryMethod;
    private final boolean overridden;
    private final boolean mapNullToDefault;
    private final String loopVariableName;

    public static class Builder {

        private Method method;
        private MappingBuilderContext ctx;
        private String dateFormat;
        private List<TypeMirror> qualifiers;
        private TypeMirror qualifyingElementTargetType;
        private NullValueMappingStrategyPrism nullValueMappingStrategy;

        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public Builder method(Method sourceMethod) {
            this.method = sourceMethod;
            return this;
        }

        public Builder dateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
            return this;
        }

        public Builder qualifiers(List<TypeMirror> qualifiers) {
            this.qualifiers = qualifiers;
            return this;
        }

        public Builder qualifyingElementTargetType(TypeMirror qualifyingElementTargetType) {
            this.qualifyingElementTargetType = qualifyingElementTargetType;
            return this;
        }

        public Builder nullValueMappingStrategy(NullValueMappingStrategyPrism nullValueMappingStrategy) {
            this.nullValueMappingStrategy = nullValueMappingStrategy;
            return this;
        }

        public IterableMappingMethod build() {


            Type sourceParameterType = first( method.getSourceParameters() ).getType();
            Type resultType = method.getResultType();

            Type sourceElementType  = sourceParameterType.isArrayType() ? sourceParameterType.getComponentType()
                : first( sourceParameterType.getTypeParameters() ).getTypeBound();
            Type targetElementType = resultType.isArrayType() ? resultType.getComponentType()
                : first( resultType.getTypeParameters() ).getTypeBound();

            String loopVariableName =
                Strings.getSaveVariableName( sourceElementType.getName(), method.getParameterNames() );

            Assignment assignment = ctx.getMappingResolver().getTargetAssignment(
                method,
                "collection element",
                sourceElementType,
                targetElementType,
                null, // there is no targetPropertyName
                dateFormat,
                qualifiers,
                qualifyingElementTargetType,
                loopVariableName,
                false
            );

            if ( assignment == null ) {
                ctx.getMessager().printMessage( method.getExecutable(), Message.ITERABLEMAPPING_MAPPING_NOT_FOUND );
            }

            // target accessor is setter, so decorate assignment as setter
            if ( resultType.isArrayType() ) {
                assignment = new LocalVarWrapper( assignment, method.getThrownTypes() );
            }
            else {
                assignment = new SetterWrapper( assignment, method.getThrownTypes() );
            }

            // mapNullToDefault
            boolean mapNullToDefault = false;
            if ( method.getMapperConfiguration() != null ) {
                 mapNullToDefault = method.getMapperConfiguration().isMapToDefault( nullValueMappingStrategy );
            }

            MethodReference factoryMethod
                = ctx.getMappingResolver().getFactoryMethod( method, method.getResultType(), null, null );

            return new IterableMappingMethod(
                    method,
                    assignment,
                    factoryMethod,
                    mapNullToDefault,
                    loopVariableName );
        }
    }


    private IterableMappingMethod(Method method, Assignment parameterAssignment, MethodReference factoryMethod,
                                  boolean mapNullToDefault, String loopVariableName ) {
        super( method );
        this.elementAssignment = parameterAssignment;
        this.factoryMethod = factoryMethod;
        this.overridden = method.overridesMethod();
        this.mapNullToDefault = mapNullToDefault;
        this.loopVariableName = loopVariableName;
    }

    public Parameter getSourceParameter() {
        for ( Parameter parameter : getParameters() ) {
            if ( !parameter.isMappingTarget() ) {
                return parameter;
            }
        }

        throw new IllegalStateException( "Method " + this + " has no source parameter." );
    }

    public Assignment getElementAssignment() {
        return elementAssignment;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();
        if ( elementAssignment != null ) {
            types.addAll( elementAssignment.getImportTypes() );
        }
        if ( ( factoryMethod == null ) && ( !isExistingInstanceMapping() ) ) {
            types.addAll( getReturnType().getImportTypes() );
        }
        return types;
    }

    public boolean isMapNullToDefault() {
        return mapNullToDefault;
    }

    public boolean isOverridden() {
        return overridden;
    }

    public String getLoopVariableName() {
        return loopVariableName;
    }

    public String getDefaultValue() {
        TypeKind kind = getResultElementType().getTypeMirror().getKind();
        switch ( kind ) {
            case BOOLEAN:
                return "false";
            case BYTE:
            case SHORT:
            case INT:
            case CHAR:  /*"'\u0000'" would have been better, but depends on platformencoding */
                return "0";
            case LONG:
                return "0L";
            case FLOAT:
                return "0.0f";
            case DOUBLE:
                return "0.0d";
            default:
                return "null";
        }
    }

    public MethodReference getFactoryMethod() {
        return this.factoryMethod;
    }

    public Type getSourceElementType() {
        Type sourceParameterType = getSourceParameter().getType();

        if ( sourceParameterType.isArrayType() ) {
            return sourceParameterType.getComponentType();
        }
        else {
            return sourceParameterType.getTypeParameters().get( 0 ).getTypeBound();
        }
    }

    public Type getResultElementType() {
        if ( getResultType().isArrayType() ) {
            return getResultType().getComponentType();
        }
        else {
            return getResultType().getTypeParameters().get( 0 );
        }
    }

    public String getIndex1Name() {
        return Strings.getSaveVariableName( "i", loopVariableName, getSourceParameter().getName(), getResultName() );
    }

    public String getIndex2Name() {
        return Strings.getSaveVariableName( "j", loopVariableName, getSourceParameter().getName(), getResultName() );
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
        IterableMappingMethod other = (IterableMappingMethod) obj;

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
