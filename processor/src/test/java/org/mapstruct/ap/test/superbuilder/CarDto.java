/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

public class CarDto extends VehicleDto {

    private final String manufacturer;

    public CarDto(int amountOfTires, String manufacturer, PassengerDto passenger) {
        super( amountOfTires, passenger );
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

}
