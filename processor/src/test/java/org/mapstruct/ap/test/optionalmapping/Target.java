/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping;

import java.util.Objects;
import java.util.Optional;

public class Target {

    private String optionalToNonOptional;
    private Optional<String> nonOptionalToOptional;
    private Optional<String> optionalToOptional;

    private SubType optionalSubTypeToNonOptional;
    private Optional<SubType> nonOptionalSubTypeToOptional;
    private Optional<SubType> optionalSubTypeToOptional;

    public String getOptionalToNonOptional() {
        return optionalToNonOptional;
    }

    public void setOptionalToNonOptional(String optionalToNonOptional) {
        this.optionalToNonOptional = optionalToNonOptional;
    }

    public Optional<String> getNonOptionalToOptional() {
        return nonOptionalToOptional;
    }

    public void setNonOptionalToOptional(Optional<String> nonOptionalToOptional) {
        this.nonOptionalToOptional = nonOptionalToOptional;
    }

    public Optional<String> getOptionalToOptional() {
        return optionalToOptional;
    }

    public void setOptionalToOptional(Optional<String> optionalToOptional) {
        this.optionalToOptional = optionalToOptional;
    }

    public SubType getOptionalSubTypeToNonOptional() {
        return optionalSubTypeToNonOptional;
    }

    public void setOptionalSubTypeToNonOptional(SubType optionalSubTypeToNonOptional) {
        this.optionalSubTypeToNonOptional = optionalSubTypeToNonOptional;
    }

    public Optional<SubType> getNonOptionalSubTypeToOptional() {
        return nonOptionalSubTypeToOptional;
    }

    public void setNonOptionalSubTypeToOptional(Optional<SubType> nonOptionalSubTypeToOptional) {
        this.nonOptionalSubTypeToOptional = nonOptionalSubTypeToOptional;
    }

    public Optional<SubType> getOptionalSubTypeToOptional() {
        return optionalSubTypeToOptional;
    }

    public void setOptionalSubTypeToOptional(Optional<SubType> optionalSubTypeToOptional) {
        this.optionalSubTypeToOptional = optionalSubTypeToOptional;
    }

    public static class SubType {

        private final int value;
        private final String b;

        public SubType(int value, String b) {
            this.value = value;
            this.b = b;
        }

        public int getValue() {
            return value;
        }

        public String getB() {
            return b;
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
            return value == subType.value && Objects.equals( b, subType.b );
        }

        @Override
        public int hashCode() {
            return Objects.hash( value, b );
        }

    }

}
