/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.beforeafter;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Source {

    private final Optional<SubType> deepOptionalToOptional;
    private final Optional<SubType> deepOptionalToNonOptional;
    private final SubType deepNonOptionalToOptional;

    private final Optional<String> shallowOptionalToOptional;
    private final Optional<String> shallowOptionalToNonOptional;
    private final String shallowNonOptionalToOptional;

    public Source(Optional<SubType> deepOptionalToOptional, Optional<SubType> deepOptionalToNonOptional,
                  SubType deepNonOptionalToOptional, Optional<String> shallowOptionalToOptional,
                  Optional<String> shallowOptionalToNonOptional, String shallowNonOptionalToOptional) {
        this.deepOptionalToOptional = deepOptionalToOptional;
        this.deepOptionalToNonOptional = deepOptionalToNonOptional;
        this.deepNonOptionalToOptional = deepNonOptionalToOptional;
        this.shallowOptionalToOptional = shallowOptionalToOptional;
        this.shallowOptionalToNonOptional = shallowOptionalToNonOptional;
        this.shallowNonOptionalToOptional = shallowNonOptionalToOptional;
    }

    public Optional<SubType> getDeepOptionalToOptional() {
        return deepOptionalToOptional;
    }

    public Optional<SubType> getDeepOptionalToNonOptional() {
        return deepOptionalToNonOptional;
    }

    public SubType getDeepNonOptionalToOptional() {
        return deepNonOptionalToOptional;
    }

    public Optional<String> getShallowOptionalToOptional() {
        return shallowOptionalToOptional;
    }

    public Optional<String> getShallowOptionalToNonOptional() {
        return shallowOptionalToNonOptional;
    }

    public String getShallowNonOptionalToOptional() {
        return shallowNonOptionalToOptional;
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
