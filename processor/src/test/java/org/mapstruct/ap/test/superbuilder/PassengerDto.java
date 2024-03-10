/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

public class PassengerDto {

    private final String name;

    public PassengerDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
