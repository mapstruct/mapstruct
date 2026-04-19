/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.Nullable;

/**
 * Source bean for verifying that existing safety guards (unboxing, defaultValue, NVPMS)
 * still emit null checks when the source is {@code @Nullable}.
 */
public class SafetyGuardSourceBean {

    private Integer nullableNumber;
    private String nullableText;

    @Nullable
    public Integer getNullableNumber() {
        return nullableNumber;
    }

    public void setNullableNumber(@Nullable Integer nullableNumber) {
        this.nullableNumber = nullableNumber;
    }

    @Nullable
    public String getNullableText() {
        return nullableText;
    }

    public void setNullableText(@Nullable String nullableText) {
        this.nullableText = nullableText;
    }
}
