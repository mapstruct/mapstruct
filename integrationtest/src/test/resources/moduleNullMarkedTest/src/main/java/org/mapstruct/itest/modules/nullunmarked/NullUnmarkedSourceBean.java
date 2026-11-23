/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.modules.nullunmarked;

/**
 * Source bean in an {@code @NullUnmarked} package (inside a {@code @NullMarked} module).
 * The package-level {@code @NullUnmarked} reverses the scope: the unannotated
 * {@code String value} getter has UNKNOWN nullability.
 */
public class NullUnmarkedSourceBean {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
