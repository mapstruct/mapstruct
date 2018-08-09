/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references;

/**
 * @author Andreas Gudian
 *
 */
public class Foo {
    private String prop1;

    public Foo() {
    }

    public Foo(String prop1) {

        this.prop1 = prop1;
    }

    public String getProp1() {
        return prop1;
    }

    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }
}
