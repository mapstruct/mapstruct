/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.methodtomethod;

public class MethodToMethodTargetBean {

    private MyTargetValue value;

    public MyTargetValue getValue() {
        return value;
    }

    public void setValue(MyTargetValue value) {
        this.value = value;
    }
}
