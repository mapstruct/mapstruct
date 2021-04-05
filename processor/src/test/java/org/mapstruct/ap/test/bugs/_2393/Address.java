/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2393;

/**
 * @author Filip Hrisafov
 */
public class Address {

    private final String city;
    private final Country country;

    public Address(String city, Country country) {
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public Country getCountry() {
        return country;
    }
}
