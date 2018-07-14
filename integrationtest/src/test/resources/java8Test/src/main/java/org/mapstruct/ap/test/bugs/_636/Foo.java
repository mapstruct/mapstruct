/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._636;

public class Foo {
    private final long id;

    public Foo(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
