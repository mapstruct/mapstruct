/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.shared;

public class AddressDto {

    private String street;
    private Integer building;
    private Integer flat;

    public AddressDto(String street, Integer building, Integer flat) {
        this.street = street;
        this.building = building;
        this.flat = flat;
    }

    public String getStreet() {
        return street;
    }

    public Integer getBuilding() {
        return building;
    }

    public Integer getFlat() {
        return flat;
    }

}
