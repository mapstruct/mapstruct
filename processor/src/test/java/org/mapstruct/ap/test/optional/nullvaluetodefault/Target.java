/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nullvaluetodefault;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Target {

    private final Optional<SubType> constructorOptionalToOptional;
    private final SubType constructorOptionalToNonOptional;
    private final Optional<SubType> constructorNonOptionalToOptional;

    private Optional<SubType> optionalToOptional;
    private SubType optionalToNonOptional;
    private Optional<SubType> nonOptionalToOptional;

    @SuppressWarnings( "VisibilityModifier" )
    public Optional<SubType> publicOptionalToOptional;
    @SuppressWarnings( "VisibilityModifier" )
    public SubType publicOptionalToNonOptional;
    @SuppressWarnings( "VisibilityModifier" )
    public Optional<SubType> publicNonOptionalToOptional;

    public Target(Optional<SubType> constructorOptionalToOptional, SubType constructorOptionalToNonOptional,
                  Optional<SubType> constructorNonOptionalToOptional) {
        this.constructorOptionalToOptional = constructorOptionalToOptional;
        this.constructorOptionalToNonOptional = constructorOptionalToNonOptional;
        this.constructorNonOptionalToOptional = constructorNonOptionalToOptional;
    }

    public Optional<SubType> getConstructorOptionalToOptional() {
        return constructorOptionalToOptional;
    }

    public SubType getConstructorOptionalToNonOptional() {
        return constructorOptionalToNonOptional;
    }

    public Optional<SubType> getConstructorNonOptionalToOptional() {
        return constructorNonOptionalToOptional;
    }

    public Optional<SubType> getOptionalToOptional() {
        return optionalToOptional;
    }

    public void setOptionalToOptional(Optional<SubType> optionalToOptional) {
        this.optionalToOptional = optionalToOptional;
    }

    public SubType getOptionalToNonOptional() {
        return optionalToNonOptional;
    }

    public void setOptionalToNonOptional(SubType optionalToNonOptional) {
        this.optionalToNonOptional = optionalToNonOptional;
    }

    public Optional<SubType> getNonOptionalToOptional() {
        return nonOptionalToOptional;
    }

    public void setNonOptionalToOptional(Optional<SubType> nonOptionalToOptional) {
        this.nonOptionalToOptional = nonOptionalToOptional;
    }

    public static class SubType {

        private final String value;

        public SubType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if ( this == o ) {
                return true;
            }
            if ( o == null || getClass() != o.getClass() ) {
                return false;
            }
            SubType subType = (SubType) o;
            return Objects.equals( value, subType.value );
        }

        @Override
        public int hashCode() {
            return Objects.hash( value );
        }
    }
}
