/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2236;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2236")
@WithClasses({
    Car.class,
    CarDto.class,
    CarMapper.class,
    Owner.class,
    Vehicle.class,
})
public class Issue2236Test {

    @ProcessorTest
    public void shouldCorrectlyMapSameTypesWithDifferentNestedMappings() {

        Vehicle vehicle = new Vehicle();
        vehicle.setType( "Sedan" );
        CarDto carDto = new CarDto();
        vehicle.setCarDto( carDto );

        carDto.setName( "Private car" );
        carDto.setOwnerNameA( "Owner A" );
        carDto.setOwnerCityA( "Zurich" );

        carDto.setOwnerNameB( "Owner B" );
        carDto.setOwnerCityB( "Bern" );

        Car car = CarMapper.INSTANCE.vehicleToCar( vehicle );

        assertThat( car ).isNotNull();
        assertThat( car.getType() ).isEqualTo( "Sedan" );
        assertThat( car.getName() ).isEqualTo( "Private car" );
        assertThat( car.getOwnerA() ).isNotNull();
        assertThat( car.getOwnerA().getName() ).isEqualTo( "Owner A" );
        assertThat( car.getOwnerA().getCity() ).isEqualTo( "Zurich" );
        assertThat( car.getOwnerB() ).isNotNull();
        assertThat( car.getOwnerB().getName() ).isEqualTo( "Owner B" );
        assertThat( car.getOwnerB().getCity() ).isEqualTo( "Bern" );
    }
}
