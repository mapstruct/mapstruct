/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */

public class DefaultPackageTest {

    @Test
    public void shouldWorkCorrectlyInDefaultPackage() {
        DefaultPackageObject.CarDto carDto = DefaultPackageObject.CarMapper.INSTANCE.carToCarDto(
            new DefaultPackageObject.Car(
                "Morris",
                5,
                DefaultPackageObject.CarType.SEDAN
            ) );

        assertThat( carDto ).isNotNull();
        assertThat( carDto.getMake() ).isEqualTo( "Morris" );
        assertThat( carDto.getSeatCount() ).isEqualTo( 5 );
        assertThat( carDto.getType() ).isEqualTo( "SEDAN" );
    }
}
