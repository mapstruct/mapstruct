/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.methodtomethod;

public class MyWrapper {

    private final String value;

    public MyWrapper( String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
