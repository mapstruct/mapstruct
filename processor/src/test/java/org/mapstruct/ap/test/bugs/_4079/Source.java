/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4079;

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

        public Nested(String foo) {
            this.foo = foo;
        }

        public String getFoo() {
            return foo;
        }
    }
}
