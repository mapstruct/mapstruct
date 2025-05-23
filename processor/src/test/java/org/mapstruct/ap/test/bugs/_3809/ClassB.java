/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3809;

public class ClassB {
    private NestedClassB param;

    public NestedClassB getParam() {
        return param;
    }

    public void setParam(NestedClassB param) {
        this.param = param;
    }
}
