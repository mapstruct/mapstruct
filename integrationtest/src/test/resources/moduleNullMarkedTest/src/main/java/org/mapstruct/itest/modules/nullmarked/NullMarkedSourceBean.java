/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.modules.nullmarked;

/**
 * Source bean in a {@code @NullMarked} module + {@code @NullMarked} package.
 * The unannotated {@code String value} getter is effectively {@code @NonNull}.
 */
public class NullMarkedSourceBean {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
