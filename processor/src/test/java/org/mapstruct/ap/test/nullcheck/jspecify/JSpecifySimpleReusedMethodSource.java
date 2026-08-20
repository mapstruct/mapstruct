/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class JSpecifySimpleReusedMethodSource {

    private @Nullable JSpecifySimpleReusedMethodValue value;

    public @Nullable JSpecifySimpleReusedMethodValue getValue() {
        return value;
    }
}
