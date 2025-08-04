/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.naming.spi.ImplementationNamingStrategy;

public class MyObject {
    private final String name;

    public String getName() {
        return name;
    }

    public MyObject(String name) {
        this.name = name;
    }
}
