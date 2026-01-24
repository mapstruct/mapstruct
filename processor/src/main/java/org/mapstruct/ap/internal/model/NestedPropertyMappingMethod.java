/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.beanmapping.PropertyEntry;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.presence.AnyPresenceChecksPresenceCheck;
import org.mapstruct.ap.internal.model.presence.NullPresenceCheck;
import org.mapstruct.ap.internal.model.presence.SuffixPresenceCheck;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.PresenceCheckAccessor;

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
            List<String> existingVariableNames = new ArrayList<>();
            Parameter sourceParameter = null;
            for ( Parameter parameter : method.getSourceParameters() ) {
                existingVariableNames.add( parameter.getName() );
                if ( sourceParameter == null && !parameter.isMappingTarget() && !parameter.isMappingContext() ) {
                    sourceParameter = parameter;
                }
            }
            final List<Type> thrownTypes = new ArrayList<>();
            List<SafePropertyEntry> safePropertyEntries = new ArrayList<>();
            if ( sourceParameter == null ) {
                throw new IllegalStateException( "Method " + method + " has no source parameter." );
            }

            String previousPropertyName = sourceParameter.getName();
            for ( int i = 0; i < propertyEntries.size(); i++ ) {
                PropertyEntry propertyEntry = propertyEntries.get( i );
                PresenceCheck presenceCheck = getPresenceCheck( propertyEntry, previousPropertyName );

                if ( i > 0 ) {
                    // If this is not the first property entry,
                    // then we might need to combine the presence check with a null check of the previous property
                    if ( presenceCheck != null ) {
                        presenceCheck = new AnyPresenceChecksPresenceCheck( Arrays.asList(
                            new NullPresenceCheck( previousPropertyName, true ),
                            presenceCheck
                        ) );
                    }
                    else {
                        presenceCheck = new NullPresenceCheck( previousPropertyName, true );
                    }
                }

                String safeName = Strings.getSafeVariableName( propertyEntry.getName(), existingVariableNames );
                String source = previousPropertyName + "." + propertyEntry.getReadAccessor().getReadValueSource();
                safePropertyEntries.add( new SafePropertyEntry(
                    propertyEntry.getType(),
                    safeName,
                    source,
                    presenceCheck
                ) );
                existingVariableNames.add( safeName );
                thrownTypes.addAll( ctx.getTypeFactory().getThrownTypes(
                        propertyEntry.getReadAccessor() ) );
                previousPropertyName = safeName;
            }
            method.addThrownTypes( thrownTypes );
            return new NestedPropertyMappingMethod( method, safePropertyEntries );
        }

        private PresenceCheck getPresenceCheck(PropertyEntry propertyEntry, String previousPropertyName) {
            PresenceCheckAccessor propertyPresenceChecker = propertyEntry.getPresenceChecker();
            if ( propertyPresenceChecker != null ) {
                return new SuffixPresenceCheck(
                    previousPropertyName,
                    propertyPresenceChecker.getPresenceCheckSuffix(),
                    true
                );
            }
            return null;
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
            types.addAll( propertyEntry.getType().getImportTypes() );
            if ( propertyEntry.getPresenceChecker() != null ) {
                types.addAll( propertyEntry.getPresenceChecker().getImportTypes() );
            }
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
        private final String source;
        private final PresenceCheck presenceChecker;
        private final Type type;

        public SafePropertyEntry(Type type, String safeName, String source, PresenceCheck presenceCheck) {
            this.safeName = safeName;
            this.source = source;
            this.presenceChecker = presenceCheck;
            this.type = type;
        }

        public String getName() {
            return safeName;
        }

        public String getSource() {
            return source;
        }

        public PresenceCheck getPresenceChecker() {
            return presenceChecker;
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

            if ( !Objects.equals( source, that.source ) ) {
                return false;
            }

            if ( !Objects.equals( presenceChecker, that.presenceChecker ) ) {
                return false;
            }

            if ( !Objects.equals( type, that.type ) ) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = source != null ? source.hashCode() : 0;
            result = 31 * result + ( presenceChecker != null ? presenceChecker.hashCode() : 0 );
            result = 31 * result + ( type != null ? type.hashCode() : 0 );
            return result;
        }
    }
}

