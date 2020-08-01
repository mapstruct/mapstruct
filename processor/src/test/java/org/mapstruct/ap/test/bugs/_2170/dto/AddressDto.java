/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2170.dto;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class AddressDto {

    private final String zipCode;
    private final List<PersonDto> people;

    public AddressDto(String zipCode,
        List<PersonDto> people) {
        this.zipCode = zipCode;
        this.people = people;
    }

    public String getZipCode() {
        return zipCode;
    }

    public List<PersonDto> getPeople() {
        return people;
    }
}
