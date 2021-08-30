/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.abstractsuperclass;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@IssueKey( "366" )
@WithClasses( { SubclassWithAbstractSuperclassMapper.class, VehicleCollection.class, AbstractVehicle.class, Car.class,
    Bike.class, VehicleCollectionDto.class, VehicleDto.class, CarDto.class, BikeDto.class, } )
public class AbstractSuperclassTest {

    @ProcessorTest
    void issue366MappingTest() {
        VehicleCollection vehicles = new VehicleCollection();
        vehicles.getVehicles().add( new Car() );
        vehicles.getVehicles().add( new Bike() );

        VehicleCollectionDto result = SubclassWithAbstractSuperclassMapper.INSTANCE.map( vehicles );

        assertThat( result.getVehicles() ).doesNotContainNull();
        assertThat( result.getVehicles() ) // remove generic so that test works.
                                          .extracting( vehicle -> (Class) vehicle.getClass() )
                                          .containsExactly( CarDto.class, BikeDto.class );
    }

    @ProcessorTest
    void mappingOfUnknownChildThrowsIllegalArgumentException() {
        VehicleCollection vehicles = new VehicleCollection();
        vehicles.getVehicles().add( new Car() );
        vehicles.getVehicles().add( new Motorcycle() );

        Assertions
                  .assertThatThrownBy( () -> SubclassWithAbstractSuperclassMapper.INSTANCE.map( vehicles ) )
                  .isInstanceOf( IllegalArgumentException.class )
                  .hasMessage(
                      "Not all subclasses are supported for this mapping. "
                          + "Missing for class org.mapstruct.ap.test.subclassmapping.abstractsuperclass.Motorcycle" );
    }
}
