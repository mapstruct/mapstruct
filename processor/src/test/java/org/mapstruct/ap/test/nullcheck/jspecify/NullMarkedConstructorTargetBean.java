/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NullMarked;

/**
 * Target bean annotated with {@code @NullMarked}.
 * The unannotated constructor parameter is effectively {@code @NonNull}.
 */
@NullMarked
public class NullMarkedConstructorTargetBean {

    private final String value;

    public NullMarkedConstructorTargetBean(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
