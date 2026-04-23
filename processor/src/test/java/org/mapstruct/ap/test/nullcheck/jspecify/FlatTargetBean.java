/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;

public class FlatTargetBean {

    private String street;
    private boolean streetSet;

    private String city;
    private boolean citySet;

    private String nullableStreet;
    private boolean nullableStreetSet;

    public String getStreet() {
        return street;
    }

    public void setStreet(@NonNull String street) {
        this.streetSet = true;
        this.street = street;
    }

    public boolean isStreetSet() {
        return streetSet;
    }

    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.citySet = true;
        this.city = city;
    }

    public boolean isCitySet() {
        return citySet;
    }

    public String getNullableStreet() {
        return nullableStreet;
    }

    public void setNullableStreet(String nullableStreet) {
        this.nullableStreetSet = true;
        this.nullableStreet = nullableStreet;
    }

    public boolean isNullableStreetSet() {
        return nullableStreetSet;
    }
}
