/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance;

public class TargetBase {

    //CHECKSTYLE:OFF
    public Long publicFoo;
    //CHECKSTYLE:ON
    private Long foo;

    public Long getFoo() {
        return foo;
    }

    public void setFoo(Long foo) {
        this.foo = foo;
    }
}
