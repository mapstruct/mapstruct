/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.sealedsubclass;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SealedSubclassTest {

    @Test
    public void mappingIsDoneUsingSubclassMapping() {
        VehicleCollection vehicles = new VehicleCollection();
        vehicles.getVehicles().add( new Car() );
        vehicles.getVehicles().add( new Bike() );
        vehicles.getVehicles().add( new Harley() );
        vehicles.getVehicles().add( new Davidson() );

        VehicleCollectionDto result = SealedSubclassMapper.INSTANCE.map( vehicles );

        assertThat( result.getVehicles() ).doesNotContainNull();
        assertThat( result.getVehicles() ) // remove generic so that test works.
            .extracting( vehicle -> (Class) vehicle.getClass() )
            .containsExactly( CarDto.class, BikeDto.class, HarleyDto.class, DavidsonDto.class );
    }

    @Test
    public void inverseMappingIsDoneUsingSubclassMapping() {
        VehicleCollectionDto vehicles = new VehicleCollectionDto();
        vehicles.getVehicles().add( new CarDto() );
        vehicles.getVehicles().add( new BikeDto() );
        vehicles.getVehicles().add( new HarleyDto() );
        vehicles.getVehicles().add( new DavidsonDto() );

        VehicleCollection result = SealedSubclassMapper.INSTANCE.mapInverse( vehicles );

        assertThat( result.getVehicles() ).doesNotContainNull();
        assertThat( result.getVehicles() ) // remove generic so that test works.
            .extracting( vehicle -> (Class) vehicle.getClass() )
            .containsExactly( Car.class, Bike.class, Harley.class, Davidson.class );
    }

    @Test
    public void subclassMappingInheritsInverseMapping() {
        VehicleCollectionDto vehiclesDto = new VehicleCollectionDto();
        CarDto carDto = new CarDto();
        carDto.setMaker( "BenZ" );
        vehiclesDto.getVehicles().add( carDto );

        VehicleCollection result = SealedSubclassMapper.INSTANCE.mapInverse( vehiclesDto );

        assertThat( result.getVehicles() )
            .extracting( Vehicle::getVehicleManufacturingCompany )
            .containsExactly( "BenZ" );
    }
}
