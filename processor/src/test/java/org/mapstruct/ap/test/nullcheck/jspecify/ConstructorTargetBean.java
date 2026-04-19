/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class ConstructorTargetBean {

    private final String nonNullParam;
    private final String nullableParam;
    private final String unannotatedParam;

    public ConstructorTargetBean(@NonNull String nonNullParam,
                                 @Nullable String nullableParam,
                                 String unannotatedParam) {
        this.nonNullParam = nonNullParam;
        this.nullableParam = nullableParam;
        this.unannotatedParam = unannotatedParam;
    }

    public String getNonNullParam() {
        return nonNullParam;
    }

    public String getNullableParam() {
        return nullableParam;
    }

    public String getUnannotatedParam() {
        return unannotatedParam;
    }
}
