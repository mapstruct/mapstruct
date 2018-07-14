/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.oneway;

public class Target {

    private Long foo;
    private final int bar = 23;
    private long qux;

    public void setFoo(Long foo) {
        this.foo = foo;
    }

    public Long retrieveFoo() {
        return foo;
    }

    public int getBar() {
        return bar;
    }

    public void setQux(long qux) {
        this.qux = qux;
    }

    public long getQux() {
        return qux;
    }
}
