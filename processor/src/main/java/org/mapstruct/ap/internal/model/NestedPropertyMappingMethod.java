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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.PropertyEntry;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.ValueProvider;

/**
 * This method is used to convert the nested properties as listed in propertyEntries into a method
 * that creates a mapping from the start of this list to the end of the list.
 * <p>
 * So, say that the start of the list is of TypeA and the end of the list is of TypeB than the forged method
 * will create a forged mapping method: TypeB methodName( TypeA in ).
 *
 * @author Sjaak Derksen
 */
public class NestedPropertyMappingMethod extends MappingMethod {

    private final List<SafePropertyEntry> safePropertyEntries;

    public static class Builder {

        private MappingBuilderContext ctx;
        private ForgedMethod method;
        private List<PropertyEntry> propertyEntries;

        public Builder method( ForgedMethod sourceMethod ) {
            this.method = sourceMethod;
            return this;
        }

        public Builder propertyEntries( List<PropertyEntry> propertyEntries ) {
            this.propertyEntries = propertyEntries;
            return this;
        }

        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public NestedPropertyMappingMethod build() {
            List<String> existingVariableNames = new ArrayList<String>();
            for ( Parameter parameter : method.getSourceParameters() ) {
                existingVariableNames.add( parameter.getName() );
            }
            final List<Type> thrownTypes = new ArrayList<Type>();
            List<SafePropertyEntry> safePropertyEntries = new ArrayList<SafePropertyEntry>();
            for ( PropertyEntry propertyEntry : propertyEntries ) {
                String safeName = Strings.getSaveVariableName( propertyEntry.getName(), existingVariableNames );
                safePropertyEntries.add( new SafePropertyEntry( propertyEntry, safeName ) );
                existingVariableNames.add( safeName );
                thrownTypes.addAll( ctx.getTypeFactory().getThrownTypes(
                        propertyEntry.getReadAccessor() ) );
            }
            method.addThrownTypes( thrownTypes );
            return new NestedPropertyMappingMethod( method, safePropertyEntries );
        }
    }

    private NestedPropertyMappingMethod( ForgedMethod method, List<SafePropertyEntry> sourcePropertyEntries ) {
        super( method );
        this.safePropertyEntries = sourcePropertyEntries;
    }

    public Parameter getSourceParameter() {
        for ( Parameter parameter : getParameters() ) {
            if ( !parameter.isMappingTarget() && !parameter.isMappingContext() ) {
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

        if ( !super.equals( obj ) ) {
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

        if ( !safePropertyEntries.equals( other.safePropertyEntries ) ) {
            return false;
        }

        return true;
    }

    public static class SafePropertyEntry {

        private final String safeName;
        private final String readAccessorName;
        private final String presenceCheckerName;
        private final Type type;

        public SafePropertyEntry(PropertyEntry entry, String safeName) {
            this.safeName = safeName;
            this.readAccessorName = ValueProvider.of( entry.getReadAccessor() ).getValue();
            if ( entry.getPresenceChecker() != null ) {
                this.presenceCheckerName = entry.getPresenceChecker().getSimpleName().toString();
            }
            else {
                this.presenceCheckerName = null;
            }
            this.type = entry.getType();
        }

        public String getName() {
            return safeName;
        }

        public String getAccessorName() {
            return readAccessorName;
        }

        public String getPresenceCheckerName() {
            return presenceCheckerName;
        }

        public Type getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if ( this == o ) {
                return true;
            }
            if ( !( o instanceof SafePropertyEntry ) ) {
                return false;
            }

            SafePropertyEntry that = (SafePropertyEntry) o;

            if ( readAccessorName != null ? !readAccessorName.equals( that.readAccessorName ) :
                that.readAccessorName != null ) {
                return false;
            }

            if ( presenceCheckerName != null ? !presenceCheckerName.equals( that.presenceCheckerName ) :
                that.presenceCheckerName != null ) {
                return false;
            }

            if ( type != null ? !type.equals( that.type ) : that.type != null ) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int result = readAccessorName != null ? readAccessorName.hashCode() : 0;
            result = 31 * result + ( presenceCheckerName != null ? presenceCheckerName.hashCode() : 0 );
            result = 31 * result + ( type != null ? type.hashCode() : 0 );
            return result;
        }
    }
}

