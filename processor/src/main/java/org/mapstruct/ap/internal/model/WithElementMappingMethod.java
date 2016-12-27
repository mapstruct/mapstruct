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

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.lang.model.type.TypeKind;

import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.util.Strings;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which does mapping of typed based methods.
 * For example Iterable or Stream.
 * The typed elements are mapped either by a {@link TypeConversion} or another mapping method.
 *
 * @author Filip Hrisafov
 */
abstract class WithElementMappingMethod extends MappingMethod {
    private final Assignment elementAssignment;
    private final MethodReference factoryMethod;
    private final boolean overridden;
    private final boolean mapNullToDefault;
    private final String loopVariableName;
    private final SelectionParameters selectionParameters;

    WithElementMappingMethod(Method method, Assignment parameterAssignment, MethodReference factoryMethod,
                                  boolean mapNullToDefault, String loopVariableName,
                                  List<LifecycleCallbackMethodReference> beforeMappingReferences,
                                  List<LifecycleCallbackMethodReference> afterMappingReferences,
                                  SelectionParameters selectionParameters, ForgedMethod forgedMethod) {
        super( method, beforeMappingReferences, afterMappingReferences,
            forgedMethod == null ? Collections.<ForgedMethod>emptyList() :
                java.util.Collections.singletonList( forgedMethod )
        );
        this.elementAssignment = parameterAssignment;
        this.factoryMethod = factoryMethod;
        this.overridden = method.overridesMethod();
        this.mapNullToDefault = mapNullToDefault;
        this.loopVariableName = loopVariableName;
        this.selectionParameters = selectionParameters;
    }

    public Parameter getSourceParameter() {
        for ( Parameter parameter : getParameters() ) {
            if ( !parameter.isMappingTarget() && !parameter.isMappingContext() ) {
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
            if ( getReturnType().getImplementationType() != null ) {
                types.addAll( getReturnType().getImplementationType().getImportTypes() );
            }
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

    public abstract Type getResultElementType();

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
        WithElementMappingMethod other = (WithElementMappingMethod) obj;

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

        if ( this.selectionParameters != null ) {
            if ( !this.selectionParameters.equals( other.selectionParameters ) ) {
                return false;
            }
        }
        else if ( other.selectionParameters != null ) {
            return false;
        }

        return isMapNullToDefault() == other.isMapNullToDefault();
    }

}
