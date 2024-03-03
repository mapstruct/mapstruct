/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

public abstract class AbstractVehicleDto {

    private final int amountOfTires;

    public AbstractVehicleDto(int amountOfTires) {
        this.amountOfTires = amountOfTires;
    }

    public int getAmountOfTires() {
        return amountOfTires;
    }

}
