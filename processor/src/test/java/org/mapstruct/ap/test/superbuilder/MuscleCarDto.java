/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

public class MuscleCarDto extends CarDto {

    private final float horsePower;

    public MuscleCarDto(int amountOfTires, String manufacturer, PassengerDto passenger, float horsePower) {
        super( amountOfTires, manufacturer, passenger );
        this.horsePower = horsePower;
    }

    public float getHorsePower() {
        return horsePower;
    }

}
