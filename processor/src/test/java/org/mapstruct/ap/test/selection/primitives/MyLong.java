/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.primitives;

/**
 *
 * @author Sjaak Derksen
 */
public class MyLong {

    private final Long value;

    public MyLong( Long value ) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

}
