/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;

/**
 * A {@code @NullMarked} outer class with a {@code @NullUnmarked} nested target class.
 * The nested class's unannotated constructor parameter must have unknown nullability.
 */
@NullMarked
public final class NullUnmarkedConstructorTargetBean {

    private NullUnmarkedConstructorTargetBean() {
    }

    @NullUnmarked
    public static class Inner {

        private final String value;

        public Inner(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
