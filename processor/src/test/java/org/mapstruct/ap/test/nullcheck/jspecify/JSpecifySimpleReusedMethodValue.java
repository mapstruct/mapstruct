/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class JSpecifySimpleReusedMethodValue {

    private final String value;

    public JSpecifySimpleReusedMethodValue(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
