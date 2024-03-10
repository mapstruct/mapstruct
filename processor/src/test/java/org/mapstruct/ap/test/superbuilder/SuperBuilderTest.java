/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.superbuilder;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the mapping of models annotated with @SuperBuilder as source and target.
 *
 * @author Oliver Erhart
 */
@WithClasses({
    AbstractVehicleDto.class,
    CarDto.class,
    InheritedAbstractCarDto.class,
    MuscleCarDto.class,
    PassengerDto.class,
    VehicleDto.class,
    AbstractVehicle.class,
    Car.class,
    ChainedAccessorsCar.class,
    ChainedAccessorsVehicle.class,
    InheritedAbstractCar.class,
    MuscleCar.class,
    Passenger.class,
    Vehicle.class,
    CarMapper.class
})
@IssueKey("3524")
public class SuperBuilderTest {

    @ProcessorTest
    public void simpleMapping() {

        PassengerDto passenger = new PassengerDto( "Tom" );
        CarDto carDto = new CarDto( 4, "BMW", passenger );

        Car car = CarMapper.INSTANCE.carDtoToCar( carDto );

        assertThat( car.getManufacturer() ).isEqualTo( "BMW" );
        assertThat( car.getAmountOfTires() ).isEqualTo( 4 );
        assertThat( car.getPassenger().getName() ).isEqualTo( "Tom" );
    }

    @ProcessorTest
    public void simpleMappingInverse() {

        Passenger passenger = Passenger.builder().name( "Tom" ).build();
        Car car = Car.builder()
            .manufacturer( "BMW" )
            .amountOfTires( 4 )
            .passenger( passenger )
            .build();

        CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

        assertThat( carDto.getManufacturer() ).isEqualTo( "BMW" );
        assertThat( carDto.getTireCount() ).isEqualTo( 4 );
        assertThat( carDto.getPassenger().getName() ).isEqualTo( "Tom" );
    }

    @ProcessorTest
    public void chainedMapping() {

        PassengerDto passenger = new PassengerDto( "Tom" );
        CarDto carDto = new CarDto( 4, "BMW", passenger );

        ChainedAccessorsCar car = CarMapper.INSTANCE.carDtoToChainedAccessorsCar( carDto );

        assertThat( car.getManufacturer() ).isEqualTo( "BMW" );
        assertThat( car.getAmountOfTires() ).isEqualTo( 4 );
        assertThat( car.getPassenger().getName() ).isEqualTo( "Tom" );
    }

    @ProcessorTest
    public void chainedMappingInverse() {

        Passenger passenger = Passenger.builder().name( "Tom" ).build();
        ChainedAccessorsCar chainedAccessorsCar = ChainedAccessorsCar.builder()
            .manufacturer( "BMW" )
            .amountOfTires( 4 )
            .passenger( passenger )
            .build();

        CarDto carDto = CarMapper.INSTANCE.chainedAccessorsCarToCarDto( chainedAccessorsCar );

        assertThat( carDto.getManufacturer() ).isEqualTo( "BMW" );
        assertThat( carDto.getTireCount() ).isEqualTo( 4 );
        assertThat( carDto.getPassenger().getName() ).isEqualTo( "Tom" );
    }

    @ProcessorTest
    public void inheritedAbstractMapping() {

        PassengerDto passenger = new PassengerDto( "Tom" );
        CarDto carDto = new CarDto( 4, "BMW", passenger );

        InheritedAbstractCar car = CarMapper.INSTANCE.carDtoToInheritedAbstractCar( carDto );

        assertThat( car.getManufacturer() ).isEqualTo( "BMW" );
        assertThat( car.getAmountOfTires() ).isEqualTo( 4 );
        assertThat( car.getPassenger().getName() ).isEqualTo( "Tom" );
    }

    @ProcessorTest
    public void inheritedAbstractMappingInverse() {

        Passenger passenger = Passenger.builder().name( "Tom" ).build();
        InheritedAbstractCar inheritedAbstractCar = InheritedAbstractCar.builder()
            .manufacturer( "BMW" )
            .amountOfTires( 4 )
            .passenger( passenger )
            .build();

        CarDto carDto = CarMapper.INSTANCE.inheritedAbstractCarToCarDto( inheritedAbstractCar );

        assertThat( carDto.getManufacturer() ).isEqualTo( "BMW" );
        assertThat( carDto.getTireCount() ).isEqualTo( 4 );
        assertThat( carDto.getPassenger().getName() ).isEqualTo( "Tom" );
    }

    @ProcessorTest
    public void secondLevelMapping() {

        PassengerDto passenger = new PassengerDto( "Tom" );
        CarDto carDto = new CarDto( 4, "BMW", passenger );

        MuscleCar car = CarMapper.INSTANCE.carDtoToMuscleCar( carDto );

        assertThat( car.getManufacturer() ).isEqualTo( "BMW" );
        assertThat( car.getAmountOfTires() ).isEqualTo( 4 );
        assertThat( car.getHorsePower() ).isEqualTo( 140.5f );
        assertThat( car.getPassenger().getName() ).isEqualTo( "Tom" );
    }

    @ProcessorTest
    public void secondLevelMappingInverse() {

        Passenger passenger = Passenger.builder().name( "Tom" ).build();
        MuscleCar muscleCar = MuscleCar.builder()
            .manufacturer( "BMW" )
            .amountOfTires( 4 )
            .passenger( passenger )
            .build();

        CarDto carDto = CarMapper.INSTANCE.muscleCarToCarDto( muscleCar );

        assertThat( carDto.getManufacturer() ).isEqualTo( "BMW" );
        assertThat( carDto.getTireCount() ).isEqualTo( 4 );
        assertThat( carDto.getPassenger().getName() ).isEqualTo( "Tom" );
    }

}
