/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.nullmarkedpackage;

/**
 * Source bean in a @NullMarked package.
 * Unannotated getter is effectively @NonNull.
 */
public class PackageNullMarkedSourceBean {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
