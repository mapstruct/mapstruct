/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.jdk17.sealed;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.Compiler;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Motor.class,
    MotorDto.class,
    SealedSubclassMapper.class,
    Vehicle.class,
    VehicleCollection.class,
    VehicleCollectionDto.class,
    VehicleDto.class
})
public class SealedSubclassTest {

    @ProcessorTest(Compiler.JDK)
    public void mappingIsDoneUsingSubclassMapping() {
        VehicleCollection vehicles = new VehicleCollection();
        vehicles.getVehicles().add( new Vehicle.Car() );
        vehicles.getVehicles().add( new Vehicle.Bike() );
        vehicles.getVehicles().add( new Motor.Harley() );
        vehicles.getVehicles().add( new Motor.Davidson() );

        VehicleCollectionDto result = SealedSubclassMapper.INSTANCE.map( vehicles );

        assertThat( result.getVehicles() ).doesNotContainNull();
        assertThat( result.getVehicles() ) // remove generic so that test works.
            .extracting( vehicle -> (Class) vehicle.getClass() )
            .containsExactly(
                VehicleDto.CarDto.class,
                VehicleDto.BikeDto.class,
                MotorDto.HarleyDto.class,
                MotorDto.DavidsonDto.class
            );
    }

    @ProcessorTest(Compiler.JDK)
    public void inverseMappingIsDoneUsingSubclassMapping() {
        VehicleCollectionDto vehicles = new VehicleCollectionDto();
        vehicles.getVehicles().add( new VehicleDto.CarDto() );
        vehicles.getVehicles().add( new VehicleDto.BikeDto() );
        vehicles.getVehicles().add( new MotorDto.HarleyDto() );
        vehicles.getVehicles().add( new MotorDto.DavidsonDto() );

        VehicleCollection result = SealedSubclassMapper.INSTANCE.mapInverse( vehicles );

        assertThat( result.getVehicles() ).doesNotContainNull();
        assertThat( result.getVehicles() ) // remove generic so that test works.
            .extracting( vehicle -> (Class) vehicle.getClass() )
            .containsExactly(
                Vehicle.Car.class,
                Vehicle.Bike.class,
                Motor.Harley.class,
                Motor.Davidson.class
            );
    }

    @ProcessorTest(Compiler.JDK)
    public void subclassMappingInheritsInverseMapping() {
        VehicleCollectionDto vehiclesDto = new VehicleCollectionDto();
        VehicleDto.CarDto carDto = new VehicleDto.CarDto();
        carDto.setMaker( "BenZ" );
        vehiclesDto.getVehicles().add( carDto );

        VehicleCollection result = SealedSubclassMapper.INSTANCE.mapInverse( vehiclesDto );

        assertThat( result.getVehicles() )
            .extracting( Vehicle::getVehicleManufacturingCompany )
            .containsExactly( "BenZ" );
    }
}
