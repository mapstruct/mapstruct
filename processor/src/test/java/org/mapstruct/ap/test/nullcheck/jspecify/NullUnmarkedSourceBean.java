/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;

/**
 * A {@code @NullMarked} outer class with a {@code @NullUnmarked} nested class.
 * The nested class's unannotated getter must have unknown nullability — the closer
 * {@code @NullUnmarked} takes precedence over the outer {@code @NullMarked} scope.
 */
@NullMarked
public final class NullUnmarkedSourceBean {

    private NullUnmarkedSourceBean() {
    }

    @NullUnmarked
    public static class Inner {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
