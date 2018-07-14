/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedtarget;

public class Source {

    private Long foo;

    private String qux;

    public Long getFoo() {
        return foo;
    }

    public void setFoo(Long foo) {
        this.foo = foo;
    }

    public String getQux() {
        return qux;
    }

    public void setQux(String qux) {
        this.qux = qux;
    }
}
