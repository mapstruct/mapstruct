/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.propertyname;

/**
 * @author Nikola Ivačič
 */
public class AddressDto implements DomainModel {
    private final String street;

    public AddressDto(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }
}
