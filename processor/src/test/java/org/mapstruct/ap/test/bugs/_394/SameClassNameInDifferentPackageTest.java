/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._394;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.mapstruct.ap.test.bugs._394.source.AnotherCar;
import org.mapstruct.ap.test.bugs._394.source.Cars;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

@WithClasses( {
    SameNameForSourceAndTargetCarsMapper.class,
    Cars.class,
    org.mapstruct.ap.test.bugs._394._target.Cars.class,
    AnotherCar.class,
    org.mapstruct.ap.test.bugs._394._target.AnotherCar.class
} )
@IssueKey("394")
public class SameClassNameInDifferentPackageTest {

    @ProcessorTest
    public void shouldCreateMapMethodImplementation() {
        Map<String, AnotherCar> values = new HashMap<String, AnotherCar>();
        //given
        AnotherCar honda = new AnotherCar( "Honda", 2 );
        AnotherCar toyota = new AnotherCar( "Toyota", 2);
        values.put( "Honda", honda );
        values.put( "Toyota", toyota );

        Cars cars = new Cars();
        cars.setMakeToCar( values );
        org.mapstruct.ap.test.bugs._394._target.Cars targetCars = SameNameForSourceAndTargetCarsMapper.INSTANCE
                .sourceCarsToTargetCars( cars );
        assertThat( targetCars ).isNotNull();
        assertThat( targetCars.getMakeToCar() ).hasSize( 2 );
    }
}
