/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.nullmarkedpackage;

/**
 * Target bean in a @NullMarked package.
 * Unannotated setter parameter is effectively @NonNull.
 */
public class PackageNullMarkedTargetBean {

    private String value;
    private boolean valueSet;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.valueSet = true;
        this.value = value;
    }

    public boolean isValueSet() {
        return valueSet;
    }
}
