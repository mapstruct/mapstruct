/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3331;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Filip Hrisafov
 */
@IssueKey("3331")
@WithClasses({
    Issue3331Mapper.class,
    Vehicle.class,
    VehicleDto.class,
})
class Issue3331Test {

    @ProcessorTest
    void shouldCorrectCompileAndThrowExceptionOnRuntime() {
        VehicleDto target = Issue3331Mapper.INSTANCE.map( new Vehicle.Car( "Test car", 4 ) );

        assertThat( target.getName() ).isEqualTo( "noname" );
        assertThat( target )
            .isInstanceOfSatisfying( VehicleDto.Car.class, car -> {
                assertThat( car.getNumOfDoors() ).isEqualTo( 4 );
            } );

        target = Issue3331Mapper.INSTANCE.map( new Vehicle.Motorbike( "Test bike", true ) );

        assertThat( target.getName() ).isEqualTo( "noname" );
        assertThat( target )
            .isInstanceOfSatisfying( VehicleDto.Motorbike.class, bike -> {
                assertThat( bike.isAllowedForMinor() ).isTrue();
            } );

        assertThatThrownBy( () -> Issue3331Mapper.INSTANCE.map( new Vehicle.Truck( "Test truck", 3 ) ) )
            .isInstanceOf( IllegalArgumentException.class )
            .hasMessage( "Not all subclasses are supported for this mapping. Missing for " + Vehicle.Truck.class );
    }
}
