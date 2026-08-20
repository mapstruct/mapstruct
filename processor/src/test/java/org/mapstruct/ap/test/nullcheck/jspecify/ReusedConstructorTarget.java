/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;

public class ReusedConstructorTarget {

    private final ReusedTargetValue value;

    public ReusedConstructorTarget(@NonNull ReusedTargetValue value) {
        this.value = value;
    }

    public ReusedTargetValue getValue() {
        return value;
    }
}
