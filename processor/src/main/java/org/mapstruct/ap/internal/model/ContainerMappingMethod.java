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
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.util.Strings;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which does mapping of generic types.
 * For example Iterable or Stream.
 * The generic elements are mapped either by a {@link TypeConversion} or another mapping method.
 *
 * @author Filip Hrisafov
 */
public abstract class ContainerMappingMethod extends NormalTypeMappingMethod {
    private final Assignment elementAssignment;
    private final String loopVariableName;
    private final SelectionParameters selectionParameters;
    private final String index1Name;
    private final String index2Name;
    private IterableCreation iterableCreation;

    ContainerMappingMethod(Method method, Collection<String> existingVariables, Assignment parameterAssignment,
        MethodReference factoryMethod, boolean mapNullToDefault, String loopVariableName,
        List<LifecycleCallbackMethodReference> beforeMappingReferences,
        List<LifecycleCallbackMethodReference> afterMappingReferences,
        SelectionParameters selectionParameters) {
        super( method, existingVariables, factoryMethod, mapNullToDefault, beforeMappingReferences,
            afterMappingReferences );
        this.elementAssignment = parameterAssignment;
        this.loopVariableName = loopVariableName;
        this.selectionParameters = selectionParameters;
        this.index1Name = Strings.getSaveVariableName( "i", existingVariables );
        this.index2Name = Strings.getSaveVariableName( "j", existingVariables );
    }

    public Parameter getSourceParameter() {
        for ( Parameter parameter : getParameters() ) {
            if ( !parameter.isMappingTarget() && !parameter.isMappingContext() ) {
                return parameter;
            }
        }

        throw new IllegalStateException( "Method " + this + " has no source parameter." );
    }

    public IterableCreation getIterableCreation() {
        if ( iterableCreation == null ) {
            iterableCreation = IterableCreation.create( this, getSourceParameter() );
        }
        return iterableCreation;
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

        if ( iterableCreation != null ) {
            types.addAll( iterableCreation.getImportTypes() );
        }
        return types;
    }

    public String getLoopVariableName() {
        return loopVariableName;
    }

    public abstract Type getResultElementType();

    public String getIndex1Name() {
        return index1Name;
    }

    public String getIndex2Name() {
        return index2Name;
    }

    @Override
    public int hashCode() {
        //Needed for Checkstyle, otherwise it fails due to EqualsHashCode rule
        return super.hashCode();
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

        if ( !super.equals( obj ) ) {
            return false;
        }

        ContainerMappingMethod other = (ContainerMappingMethod) obj;

        if ( this.selectionParameters != null ) {
            if ( !this.selectionParameters.equals( other.selectionParameters ) ) {
                return false;
            }
        }
        else if ( other.selectionParameters != null ) {
            return false;
        }

        return true;
    }

}
