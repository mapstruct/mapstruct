/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.naming.spi.ImplementationNamingStrategy;

public class MyObjectDto {
    private final String name;

    public MyObjectDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
