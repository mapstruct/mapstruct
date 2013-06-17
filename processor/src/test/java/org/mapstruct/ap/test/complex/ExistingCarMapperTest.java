/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.mapstruct.ap.test.complex.dest.CarDto;
import org.mapstruct.ap.test.complex.dest.MotorDto;
import org.mapstruct.ap.test.complex.dest.MotorizedObjectDto;
import org.mapstruct.ap.test.complex.dest.PersonDto;
import org.mapstruct.ap.test.complex.source.Car;
import org.mapstruct.ap.test.complex.source.Category;
import org.mapstruct.ap.test.complex.source.Motor;
import org.mapstruct.ap.test.complex.source.MotorizedObject;
import org.mapstruct.ap.test.complex.source.Person;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.mapstruct.ap.testutil.WithClasses;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

@WithClasses({
    Car.class,
    CarDto.class,
    Person.class,
    PersonDto.class,
    ExistingCarMapper.class,
    Category.class,
    DateMapper.class,
    MotorizedObject.class,
    MotorizedObjectDto.class,
    Motor.class,
    MotorDto.class
})
public class ExistingCarMapperTest extends MapperTestBase {

    @Test
    public void shouldProvideMapperInstance() throws Exception {
        assertThat( ExistingCarMapper.INSTANCE ).isNotNull();
    }

    @Test
    public void shouldMapAttributeByName() {
        // given
        Car car = new Car(
            "Morris",
            2,
            new GregorianCalendar( 1980, 0, 1 ).getTime(),
            new Person( "Bob" ),
            new ArrayList<Person>(),
            40,
            new Motor( "V8" )
        );

        CarDto carDto = new CarDto();
        // when
        ExistingCarMapper.INSTANCE.carToCarDto( car, carDto );

        // then
        assertThat( carDto ).isNotNull();
        assertThat( carDto.getMake() ).isEqualTo( car.getMake() );
        assertThat( carDto.getMaxSpeed() ).isEqualTo( car.getMaxSpeed() );
    }

    @Test
    public void shouldMapReferenceAttribute() {
        // given
        Car car = new Car(
            "Morris",
            2,
            new GregorianCalendar( 1980, 0, 1 ).getTime(),
            new Person( "Bob" ),
            new ArrayList<Person>(),
            40,
            new Motor( "V8" )
        );

        // when
        CarDto carDto = new CarDto();
        ExistingCarMapper.INSTANCE.carToCarDto( car, carDto );
        
        // then
        assertThat( carDto ).isNotNull();
        assertThat( carDto.getDriver() ).isNotNull();
        assertThat( carDto.getDriver().getName() ).isEqualTo( "Bob" );

        assertThat( carDto.getMotor() ).isNotNull();
        assertThat( carDto.getMotor().getType() ).isEqualTo( "V8" );
    }

    @Test
    public void shouldMapAttributeWithCustomMapping() {
        // given
        Car car = new Car(
            "Morris",
            2,
            new GregorianCalendar( 1980, 0, 1 ).getTime(),
            new Person( "Bob" ),
            new ArrayList<Person>(),
            40,
            new Motor( "V8" )
        );

        // when
        CarDto carDto = new CarDto();
        ExistingCarMapper.INSTANCE.carToCarDto( car, carDto );

        // then
        assertThat( carDto ).isNotNull();
        assertThat( carDto.getSeatCount() ).isEqualTo( car.getNumberOfSeats() );
    }

    @Test
    public void shouldApplyConverter() {
        // given
        Car car = new Car(
            "Morris",
            2,
            new GregorianCalendar( 1980, 0, 1 ).getTime(),
            new Person( "Bob" ),
            new ArrayList<Person>(),
            40,
            new Motor( "V8" )
        );

        // when
        CarDto carDto = new CarDto();
        ExistingCarMapper.INSTANCE.carToCarDto( car, carDto );

        // then
        assertThat( carDto ).isNotNull();
        assertThat( carDto.getManufacturingYear() ).isEqualTo( "1980" );
    }

    @Test
    public void shouldMapIterable() {
        // given
        Car car1 = new Car(
            "Morris",
            2,
            new GregorianCalendar( 1980, 0, 1 ).getTime(),
            new Person( "Bob" ),
            new ArrayList<Person>(),
            40,
            new Motor( "V8" )
        );
        Car car2 = new Car(
            "Railton",
            4,
            new GregorianCalendar( 1934, 0, 1 ).getTime(),
            new Person( "Bill" ),
            new ArrayList<Person>(),
            40,
            new Motor( "V8" )
        );

        // when
        List<CarDto> dtos = new ArrayList<CarDto>();
        ExistingCarMapper.INSTANCE.carsToCarDtos( new ArrayList<Car>( Arrays.asList( car1, car2 ) ), dtos );

        // then
        assertThat( dtos ).isNotNull();
        assertThat( dtos ).hasSize( 2 );

        assertThat( dtos.get( 0 ).getMake() ).isEqualTo( "Morris" );
        assertThat( dtos.get( 0 ).getSeatCount() ).isEqualTo( 2 );
        assertThat( dtos.get( 0 ).getManufacturingYear() ).isEqualTo( "1980" );
        assertThat( dtos.get( 0 ).getDriver().getName() ).isEqualTo( "Bob" );

        assertThat( dtos.get( 1 ).getMake() ).isEqualTo( "Railton" );
        assertThat( dtos.get( 1 ).getSeatCount() ).isEqualTo( 4 );
        assertThat( dtos.get( 1 ).getManufacturingYear() ).isEqualTo( "1934" );
        assertThat( dtos.get( 1 ).getDriver().getName() ).isEqualTo( "Bill" );
    }
}
