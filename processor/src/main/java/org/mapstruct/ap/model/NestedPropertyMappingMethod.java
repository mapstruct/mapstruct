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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.SourceReference.PropertyEntry;
import org.mapstruct.ap.util.Strings;

/**
 * This method is used to convert the nested properties as listed in propertyEntries into a method
 * that creates a mapping from the start of this list to the end of the list.
 *
 * So, say that the start of the list is of TypeA and the end of the list is of TypeB than the forged method
 * will create a forged mapping method: TypeB methodName( TypeA in ).
 *
 * @author Sjaak Derksen
 */
public class NestedPropertyMappingMethod extends MappingMethod {

    private final List<String> existingVariableNames;
    private final List<SafePropertyEntry> safePropertyEntries;

    public static class Builder {

        private Method method;
        private List<PropertyEntry> propertyEntries;

        public Builder method( Method sourceMethod ) {
            this.method = sourceMethod;
            return this;
        }

        public Builder propertyEntries( List<PropertyEntry> propertyEntries ) {
            this.propertyEntries = propertyEntries;
            return this;
        }

        public NestedPropertyMappingMethod build() {
            List<String> existingVariableNames = new ArrayList<String>();
            List<SafePropertyEntry> safePropertyEntries = new ArrayList<SafePropertyEntry>();
            for ( PropertyEntry propertyEntry : propertyEntries ) {
                safePropertyEntries.add( new SafePropertyEntry( propertyEntry, existingVariableNames ) );
            }
            return new NestedPropertyMappingMethod( method, safePropertyEntries, existingVariableNames );
        }
    }

    private NestedPropertyMappingMethod( Method method, List<SafePropertyEntry> sourcePropertyEntries,
            List<String> existingVariableNames ) {
        super( method );
        this.safePropertyEntries = sourcePropertyEntries;
        this.existingVariableNames = existingVariableNames;
    }

    public Parameter getSourceParameter() {
        for ( Parameter parameter : getParameters() ) {
            if ( !parameter.isMappingTarget() ) {
                return parameter;
            }
        }
        throw new IllegalStateException( "Method " + this + " has no source parameter." );
    }

    public List<SafePropertyEntry> getPropertyEntries() {
        return safePropertyEntries;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();
        for ( SafePropertyEntry propertyEntry : safePropertyEntries) {
            types.add( propertyEntry.getType() );
        }
        return types;
    }

    public String getTargetLocalVariable() {
        return Strings.getSaveVariableName( getReturnType().getName(), existingVariableNames );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( getReturnType() == null ) ? 0 : getReturnType().hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        NestedPropertyMappingMethod other = (NestedPropertyMappingMethod) obj;

        if ( !getReturnType().equals( other.getReturnType() ) ) {
            return false;
        }

        if ( getSourceParameters().size() != other.getSourceParameters().size() ) {
            return false;
        }

        for ( int i = 0; i < getSourceParameters().size(); i++ ) {
            if ( !getSourceParameters().get( i ).getType().equals( other.getSourceParameters().get( i ).getType() ) ) {
                return false;
            }
        }

        if ( !getName().equals( other.getName() ) ) {
            return false;
        }

        return true;
    }

    public static class SafePropertyEntry extends PropertyEntry {

        private final List<String> existingVariableNames;

        public SafePropertyEntry( PropertyEntry entry, List<String> existingVariableNames ) {
            super( entry.getName(), entry.getAccessor(), entry.getType() );
            this.existingVariableNames = existingVariableNames;
        }

        @Override
        public String getName() {
            return Strings.getSaveVariableName( super.getName(), existingVariableNames );
        }
    }

}
