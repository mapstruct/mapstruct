/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.beforeafter;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Target {

    private final Optional<SubType> deepOptionalToOptional;
    private final SubType deepOptionalToNonOptional;
    private final Optional<SubType> deepNonOptionalToOptional;

    private final Optional<String> shallowOptionalToOptional;
    private final String shallowOptionalToNonOptional;
    private final Optional<String> shallowNonOptionalToOptional;

    public Target(Optional<SubType> deepOptionalToOptional, SubType deepOptionalToNonOptional,
                  Optional<SubType> deepNonOptionalToOptional, Optional<String> shallowOptionalToOptional,
                  String shallowOptionalToNonOptional, Optional<String> shallowNonOptionalToOptional) {
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

    public SubType getDeepOptionalToNonOptional() {
        return deepOptionalToNonOptional;
    }

    public Optional<SubType> getDeepNonOptionalToOptional() {
        return deepNonOptionalToOptional;
    }

    public Optional<String> getShallowOptionalToOptional() {
        return shallowOptionalToOptional;
    }

    public String getShallowOptionalToNonOptional() {
        return shallowOptionalToNonOptional;
    }

    public Optional<String> getShallowNonOptionalToOptional() {
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
