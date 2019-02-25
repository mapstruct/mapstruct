/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.bean;

public class SomeTypeProperty {

    // CHECKSTYLE:OFF
    public SomeType publicProp;
    // CHECKSTYLE:ON

    private SomeType prop;

    public SomeType getProp() {
        return prop;
    }

    public void setProp( SomeType prop ) {
        this.prop = prop;
    }
}
