/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance;

public class SourceBase {

    //CHECKSTYLE:OFF
    public int publicFoo;
    //CHECKSTYLE:ON
    private int foo;

    public int getFoo() {
        return foo;
    }

    public void setFoo(int foo) {
        this.foo = foo;
    }
}
