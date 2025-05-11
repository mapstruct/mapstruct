/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1590;

public class Book {

    private final String name;

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
