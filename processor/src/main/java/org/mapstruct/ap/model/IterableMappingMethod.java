/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.Set;
import org.mapstruct.ap.model.assignment.TypeConversion;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.util.Strings;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one iterable type to another. The collection
 * elements are mapped either by a {@link TypeConversion} or another mapping method.
 *
 * @author Gunnar Morling
 */
public class IterableMappingMethod extends MappingMethod {

    private final Assignment elementAssignment;
    private final FactoryMethod factoryMethod;
    private final boolean overridden;

    public IterableMappingMethod(Method method, Assignment parameterAssignment, FactoryMethod factoryMethod) {
        super( method );
        this.elementAssignment = parameterAssignment;
        this.factoryMethod = factoryMethod;
        this.overridden = method.overridesMethod();
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

        return types;
    }

    public String getLoopVariableName() {
        return Strings.getSaveVariableName(
            getSourceParameter().getType().getTypeParameters().get( 0 ).getName(),
            getParameterNames()
        );
    }

    public FactoryMethod getFactoryMethod() {
        return this.factoryMethod;
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

        for (int i = 0; i < getSourceParameters().size(); i++ ) {
            if ( !getSourceParameters().get( i ).getType().getTypeParameters().get( 0 )
                    .equals( other.getSourceParameters().get( i ).getType().getTypeParameters().get( 0 ) ) ) {
                return false;
            }
        }

        return true;
    }

    public boolean isOverridden() {
        return overridden;
    }
}
