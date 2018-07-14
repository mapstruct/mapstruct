/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1482;

public enum SourceEnum {

    VAL1( "value1" ),
    VAL2( "value2" );

    private final String val;

    SourceEnum(String val) {
        this.val = val;
    }

    public String toString() {
        return val;
    }
}
