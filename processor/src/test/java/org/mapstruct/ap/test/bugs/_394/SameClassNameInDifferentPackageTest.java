/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.bugs._394;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._394.source.AnotherCar;
import org.mapstruct.ap.test.bugs._394.source.Cars;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

@WithClasses( {
    SameNameForSourceAndTargetCarsMapper.class,
    Cars.class,
    org.mapstruct.ap.test.bugs._394._target.Cars.class,
    AnotherCar.class,
    org.mapstruct.ap.test.bugs._394._target.AnotherCar.class
} )
@IssueKey("394")
@RunWith(AnnotationProcessorTestRunner.class)
public class SameClassNameInDifferentPackageTest {

    @Test
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
