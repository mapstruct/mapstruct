/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.from;

/**
 * @author Andreas Gudian
 */
public class FooWrapper {
    private Foo value;

    public Foo getValue() {
        return value;
    }

    public void setValue(Foo value) {
        this.value = value;
    }
}
