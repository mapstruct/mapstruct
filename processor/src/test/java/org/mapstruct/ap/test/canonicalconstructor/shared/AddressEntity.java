/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.shared;

public class AddressEntity {

    private String street;
    private Integer building;
    private Integer flat;

    public AddressEntity(String street, Integer building, Integer flat) {
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
