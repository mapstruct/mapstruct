/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.modules.nullunmarked;

/**
 * Target bean in an {@code @NullUnmarked} package (inside a {@code @NullMarked} module).
 * The unannotated {@code String value} setter parameter has UNKNOWN nullability.
 */
public class NullUnmarkedTargetBean {

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
