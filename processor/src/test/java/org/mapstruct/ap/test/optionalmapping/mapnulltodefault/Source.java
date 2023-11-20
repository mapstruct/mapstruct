/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.mapnulltodefault;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Source {

    private final Optional<SubType> constructorOptionalToOptional;
    private final Optional<SubType> constructorOptionalToNonOptional;
    private final SubType constructorNonOptionalToOptional;

    private Optional<SubType> optionalToOptional;
    private Optional<SubType> optionalToNonOptional;
    private SubType nonOptionalToOptional;

    public Optional<SubType> publicOptionalToOptional;
    public Optional<SubType> publicOptionalToNonOptional;
    public SubType publicNonOptionalToOptional;

    public Source(Optional<SubType> constructorOptionalToOptional, Optional<SubType> constructorOptionalToNonOptional,
                  SubType constructorNonOptionalToOptional) {
        this.constructorOptionalToOptional = constructorOptionalToOptional;
        this.constructorOptionalToNonOptional = constructorOptionalToNonOptional;
        this.constructorNonOptionalToOptional = constructorNonOptionalToOptional;
    }

    public Optional<SubType> getConstructorOptionalToOptional() {
        return constructorOptionalToOptional;
    }

    public Optional<SubType> getConstructorOptionalToNonOptional() {
        return constructorOptionalToNonOptional;
    }

    public SubType getConstructorNonOptionalToOptional() {
        return constructorNonOptionalToOptional;
    }

    public Optional<SubType> getOptionalToOptional() {
        return optionalToOptional;
    }

    public void setOptionalToOptional(Optional<SubType> optionalToOptional) {
        this.optionalToOptional = optionalToOptional;
    }

    public Optional<SubType> getOptionalToNonOptional() {
        return optionalToNonOptional;
    }

    public void setOptionalToNonOptional(Optional<SubType> optionalToNonOptional) {
        this.optionalToNonOptional = optionalToNonOptional;
    }

    public SubType getNonOptionalToOptional() {
        return nonOptionalToOptional;
    }

    public void setNonOptionalToOptional(SubType nonOptionalToOptional) {
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
