/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.methodtobuiltIn;

public class MethodBuiltInInput {

    private final String value;

    public MethodBuiltInInput( String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
