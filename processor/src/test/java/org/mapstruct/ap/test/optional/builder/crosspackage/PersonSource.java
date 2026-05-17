/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.builder.crosspackage;

public class PersonSource {
    private final String name;

    public PersonSource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
