/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.reuse;

public class Wrapper {

    private final String value;

    public Wrapper( String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
