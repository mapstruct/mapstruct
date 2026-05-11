/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Condition;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

/**
 * Two overloaded {@link Condition} methods that bind the same number of parameters (each the source value plus a
 * distinct {@link Context}). When both contexts are available neither overload is more specific than the other, so the
 * selection stays ambiguous and an error is reported.
 */
@Mapper
public interface ErroneousAmbiguousOverloadedConditionalMethodMapper {

    Target map(Source source, @Context FirstContext first, @Context SecondContext second);

    default <T> T unwrap(Nullable<T> nullable) {
        return nullable.value;
    }

    @Condition
    default <T> boolean isPresent(Nullable<T> nullable, @Context FirstContext first) {
        return nullable.isPresent();
    }

    @Condition
    default <T> boolean isPresent(Nullable<T> nullable, @Context SecondContext second) {
        return nullable.isPresent();
    }

    class FirstContext {
    }

    class SecondContext {
    }

    class Nullable<T> {

        private final T value;
        private final boolean present;

        private Nullable(T value, boolean present) {
            this.value = value;
            this.present = present;
        }

        public boolean isPresent() {
            return present;
        }
    }

    class Source {
        protected final Nullable<String> value;

        public Source(Nullable<String> value) {
            this.value = value;
        }

        public Nullable<String> getValue() {
            return value;
        }
    }

    class Target {
        protected String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
