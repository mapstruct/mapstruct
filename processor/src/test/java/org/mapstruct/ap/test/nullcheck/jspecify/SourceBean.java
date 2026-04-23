/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class SourceBean {

    private String nonNullValue;
    private String nullableValue;
    private String unannotatedValue;

    @NonNull
    public String getNonNullValue() {
        return nonNullValue;
    }

    public void setNonNullValue(String nonNullValue) {
        this.nonNullValue = nonNullValue;
    }

    @Nullable
    public String getNullableValue() {
        return nullableValue;
    }

    public void setNullableValue(String nullableValue) {
        this.nullableValue = nullableValue;
    }

    public String getUnannotatedValue() {
        return unannotatedValue;
    }

    public void setUnannotatedValue(String unannotatedValue) {
        this.unannotatedValue = unannotatedValue;
    }
}
