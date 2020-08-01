/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2170.entity;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class Address {
    private final String zipCode;
    private final List<Person> people;

    public Address(String zipCode, List<Person> people) {
        this.zipCode = zipCode;
        this.people = people;
    }

    public String getZipCode() {
        return zipCode;
    }

    public List<Person> getPeople() {
        return people;
    }
}
