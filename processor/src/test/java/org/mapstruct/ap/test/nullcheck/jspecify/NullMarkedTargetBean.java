/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Target bean annotated with {@code @NullMarked}.
 * All unannotated setter parameters are effectively {@code @NonNull}.
 */
@NullMarked
public class NullMarkedTargetBean {

    private String nonNullByDefault;
    private boolean nonNullByDefaultSet;

    private String explicitlyNullable;
    private boolean explicitlyNullableSet;

    public String getNonNullByDefault() {
        return nonNullByDefault;
    }

    public void setNonNullByDefault(String nonNullByDefault) {
        this.nonNullByDefaultSet = true;
        this.nonNullByDefault = nonNullByDefault;
    }

    public boolean isNonNullByDefaultSet() {
        return nonNullByDefaultSet;
    }

    @Nullable
    public String getExplicitlyNullable() {
        return explicitlyNullable;
    }

    public void setExplicitlyNullable(@Nullable String explicitlyNullable) {
        this.explicitlyNullableSet = true;
        this.explicitlyNullable = explicitlyNullable;
    }

    public boolean isExplicitlyNullableSet() {
        return explicitlyNullableSet;
    }
}
