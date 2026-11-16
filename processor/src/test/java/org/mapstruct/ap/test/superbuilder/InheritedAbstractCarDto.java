/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

public class InheritedAbstractCarDto extends AbstractVehicleDto {

    private final String manufacturer;

    public InheritedAbstractCarDto(int amountOfTires, String manufacturer) {
        super( amountOfTires );
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

}
