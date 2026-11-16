/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3360;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Filip Hrisafov
 */
@IssueKey("3360")
@WithClasses({
    Issue3360Mapper.class,
    Vehicle.class,
    VehicleDto.class,
})
class Issue3360Test {

    @ProcessorTest
    void shouldCompileWithoutErrorsAndWarnings() {

        Vehicle vehicle = new Vehicle.Car( "Test", "car", 4 );

        VehicleDto target = Issue3360Mapper.INSTANCE.map( vehicle );

        assertThat( target.getName() ).isEqualTo( "Test" );
        assertThat( target.getModel() ).isEqualTo( "car" );
        assertThat( target ).isInstanceOfSatisfying( VehicleDto.Car.class, car -> {
            assertThat( car.getNumOfDoors() ).isEqualTo( 4 );
        } );

        assertThatThrownBy( () -> Issue3360Mapper.INSTANCE.map( new Vehicle.Motorbike( "Test", "bike" ) ) )
            .isInstanceOf( IllegalArgumentException.class )
            .hasMessage( "Not all subclasses are supported for this mapping. Missing for " + Vehicle.Motorbike.class );
    }
}
