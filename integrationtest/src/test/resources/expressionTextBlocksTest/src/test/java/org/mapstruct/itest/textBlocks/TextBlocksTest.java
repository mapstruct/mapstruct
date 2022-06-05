/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.textBlocks;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TextBlocksTest {

    @Test
    public void textBlockExpressionShouldWork() {
        Car car = new Car();
        car.setWheelPosition( new WheelPosition( "left" ) );

        CarDto carDto = CarAndWheelMapper.INSTANCE.carDtoFromCar(car);

        assertThat( carDto ).isNotNull();
        assertThat( carDto.wheelPosition() )
            .isEqualTo( "left" );
    }
}
