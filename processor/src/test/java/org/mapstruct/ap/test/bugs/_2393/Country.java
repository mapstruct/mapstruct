/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2393;

/**
 * @author Filip Hrisafov
 */
public class Country {

    private final String name;

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
