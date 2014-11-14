/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.mapnulltodefault;

import java.util.Arrays;
import java.util.List;
import static org.fest.assertions.Assertions.assertThat;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.mapnulltodefault.source.Car;
import org.mapstruct.ap.test.mapnulltodefault.target.CarDto;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@WithClasses({
    Car.class,
    CarDto.class,
    CarMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class CarMapperTest {

    @Test
    public void shouldProvideMapperInstance() throws Exception {
        assertThat( CarMapper.INSTANCE ).isNotNull();
    }

    @Test
    public void shouldMapExpressionAndConstantRegardlessNullArg() {
        //given
        Car car = new Car( "Morris", 2 );

        //when
        CarDto carDto1 = CarMapper.INSTANCE.carToCarDto( car );

        //then
        assertThat( carDto1 ).isNotNull();
        assertThat( carDto1.getMake() ).isEqualTo( car.getMake() );
        assertThat( carDto1.getSeatCount() ).isEqualTo( car.getNumberOfSeats() );
        assertThat( carDto1.getModel() ).isEqualTo( "ModelT" );
        assertThat( carDto1.getCatalogId() ).isNotEmpty();

        //when
        CarDto carDto2 = CarMapper.INSTANCE.carToCarDto( null );

        //then
        assertThat( carDto2 ).isNotNull();
        assertThat( carDto2.getMake() ).isNull();
        assertThat( carDto2.getSeatCount() ).isEqualTo( 0 );
        assertThat( carDto2.getModel() ).isEqualTo( "ModelT" );
        assertThat( carDto2.getCatalogId() ).isNotEmpty();
    }

    @Test
    public void shouldMapExpressionAndConstantRegardlessNullArgSeveralSources() {
        //given
        Car car = new Car( "Morris", 2 );

        //when
        CarDto carDto1 = CarMapper.INSTANCE.carToCarDto( car, "ModelT" );

        //then
        assertThat( carDto1 ).isNotNull();
        assertThat( carDto1.getMake() ).isEqualTo( car.getMake() );
        assertThat( carDto1.getSeatCount() ).isEqualTo( car.getNumberOfSeats() );
        assertThat( carDto1.getCatalogId() ).isNotEmpty();

        //when
        CarDto carDto2 = CarMapper.INSTANCE.carToCarDto( null, "ModelT" );

        //then
        assertThat( carDto2 ).isNotNull();
        assertThat( carDto2.getMake() ).isNull();
        assertThat( carDto2.getSeatCount() ).isEqualTo( 0 );
        assertThat( carDto2.getModel() ).isEqualTo( "ModelT" );
        assertThat( carDto2.getCatalogId() ).isNotEmpty();
    }

    @Test
    public void shouldMapIterableWithNullArg() {

        //given
        Car car = new Car( "Morris", 2 );

        //when
        List<CarDto> carDtos1 = CarMapper.INSTANCE.carsToCarDtos( Arrays.asList( car ) );

       //then
       assertThat( carDtos1 ).isNotNull();
       assertThat( carDtos1.size() ).isEqualTo( 1 );

       //when
       List<CarDto> carDtos2 = CarMapper.INSTANCE.carsToCarDtos( null );

       //then
       assertThat( carDtos2 ).isNotNull();
       assertThat( carDtos2.isEmpty() ).isTrue();

    }
}
