/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Source bean annotated with {@code @NullMarked}.
 * All unannotated types are effectively {@code @NonNull}.
 */
@NullMarked
public class NullMarkedSourceBean {

    private String nonNullByDefault;
    private String explicitlyNullable;

    public String getNonNullByDefault() {
        return nonNullByDefault;
    }

    public void setNonNullByDefault(String nonNullByDefault) {
        this.nonNullByDefault = nonNullByDefault;
    }

    @Nullable
    public String getExplicitlyNullable() {
        return explicitlyNullable;
    }

    public void setExplicitlyNullable(@Nullable String explicitlyNullable) {
        this.explicitlyNullable = explicitlyNullable;
    }
}
