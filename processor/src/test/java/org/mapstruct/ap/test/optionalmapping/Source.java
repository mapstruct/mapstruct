/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping;

import java.util.Objects;
import java.util.Optional;

public class Source {

    private Optional<String> optionalToNonOptional;
    private String nonOptionalToOptional;
    private Optional<String> optionalToOptional;

    private Optional<SubType> optionalSubTypeToNonOptional;
    private SubType nonOptionalSubTypeToOptional;
    private Optional<SubType> optionalSubTypeToOptional;

    public Optional<String> getOptionalToNonOptional() {
        return optionalToNonOptional;
    }

    public void setOptionalToNonOptional(Optional<String> optionalToNonOptional) {
        this.optionalToNonOptional = optionalToNonOptional;
    }

    public String getNonOptionalToOptional() {
        return nonOptionalToOptional;
    }

    public void setNonOptionalToOptional(String nonOptionalToOptional) {
        this.nonOptionalToOptional = nonOptionalToOptional;
    }

    public Optional<String> getOptionalToOptional() {
        return optionalToOptional;
    }

    public void setOptionalToOptional(Optional<String> optionalToOptional) {
        this.optionalToOptional = optionalToOptional;
    }

    public Optional<SubType> getOptionalSubTypeToNonOptional() {
        return optionalSubTypeToNonOptional;
    }

    public void setOptionalSubTypeToNonOptional(Optional<SubType> optionalSubTypeToNonOptional) {
        this.optionalSubTypeToNonOptional = optionalSubTypeToNonOptional;
    }

    public SubType getNonOptionalSubTypeToOptional() {
        return nonOptionalSubTypeToOptional;
    }

    public void setNonOptionalSubTypeToOptional(SubType nonOptionalSubTypeToOptional) {
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
        private final String a;

        public SubType(int value, String a) {
            this.value = value;
            this.a = a;
        }

        public int getValue() {
            return value;
        }

        public String getA() {
            return a;
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
            return value == subType.value && Objects.equals( a, subType.a );
        }

        @Override
        public int hashCode() {
            return Objects.hash( value, a );
        }
        
    }
    
}
