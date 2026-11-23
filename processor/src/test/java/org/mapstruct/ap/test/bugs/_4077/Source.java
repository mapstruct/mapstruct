/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4077;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class Source {
    private final @Nullable Nested nested;

    public Source(@Nullable Nested nested) {
        this.nested = nested;
    }

    public @Nullable Nested getNested() {
        return nested;
    }

    public static class Nested {
        private final String foo;
        private final Integer bar;

        public Nested(String foo, Integer bar) {
            this.foo = foo;
            this.bar = bar;
        }

        public String getFoo() {
            return foo;
        }

        public Integer getBar() {
            return bar;
        }
    }
}
