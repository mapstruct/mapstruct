/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.methodtomethod;

public class MyTargetValue {

    private final String value;
    private final int factor;

    public MyTargetValue( String value, int factor ) {
        this.value = value;
        this.factor = factor;
    }

    public String getValue() {
        return value;
    }

    public int getFactor() {
        return factor;
    }
}
