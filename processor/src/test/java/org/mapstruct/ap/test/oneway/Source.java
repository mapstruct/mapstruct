/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.oneway;

public class Source {

    private final int foo = 42;
    private int bar;
    private final long qax = 23L;

    public int getFoo() {
        return foo;
    }

    public void setBar(int bar) {
        this.bar = bar;
    }

    public int retrieveBar() {
        return bar;
    }

    public long getQax() {
        return qax;
    }
}
