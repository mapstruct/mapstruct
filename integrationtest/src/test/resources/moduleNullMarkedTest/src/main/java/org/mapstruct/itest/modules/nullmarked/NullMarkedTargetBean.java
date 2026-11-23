/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.modules.nullmarked;

/**
 * Target bean in a {@code @NullMarked} module + {@code @NullMarked} package.
 * The unannotated {@code String value} setter parameter is effectively {@code @NonNull}.
 */
public class NullMarkedTargetBean {

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
