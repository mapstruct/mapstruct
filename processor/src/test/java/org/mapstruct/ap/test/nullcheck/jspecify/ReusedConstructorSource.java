/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.Nullable;

public class ReusedConstructorSource {

    private @Nullable ReusedSourceValue value;

    public @Nullable ReusedSourceValue getValue() {
        return value;
    }

    public void setValue(@Nullable ReusedSourceValue value) {
        this.value = value;
    }
}
