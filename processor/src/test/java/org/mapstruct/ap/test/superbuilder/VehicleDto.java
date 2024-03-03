/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

public class VehicleDto {

    private final int tireCount;
    private final PassengerDto passenger;

    public VehicleDto(int tireCount, PassengerDto passenger) {
        this.tireCount = tireCount;
        this.passenger = passenger;
    }

    public int getTireCount() {
        return tireCount;
    }

    public PassengerDto getPassenger() {
        return passenger;
    }
}
