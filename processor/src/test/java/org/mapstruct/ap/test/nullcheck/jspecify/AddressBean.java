/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class AddressBean {

    private String street;
    private String city;

    @NonNull
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Nullable
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
