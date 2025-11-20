/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.abstractsuperclass;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@IssueKey("3821")
@WithClasses({
    Bike.class,
    BikeDto.class,
    Car.class,
    CarDto.class,
    Motorcycle.class,
    VehicleCollection.class,
    VehicleCollectionDto.class,
    AbstractVehicle.class,
    VehicleDto.class,
    CustomSubclassMappingException.class,
    CustomExceptionSubclassMapper.class
})
public class CustomSubclassMappingExceptionTest {

    private static final String EXPECTED_ERROR_MESSAGE = "Not all subclasses are supported for this mapping. "
        + "Missing for class org.mapstruct.ap.test.subclassmapping.abstractsuperclass.Motorcycle";

    @ProcessorTest
    void customExceptionIsThrownForUnknownSubclass() {
        VehicleCollection vehicles = new VehicleCollection();
        vehicles.getVehicles().add( new Car() );
        vehicles.getVehicles().add( new Motorcycle() ); // undefine subclass

        assertThatThrownBy( () -> CustomExceptionSubclassMapper.INSTANCE.mapInverse( vehicles ) )
            .isInstanceOf( CustomSubclassMappingException.class )
            .hasMessage( EXPECTED_ERROR_MESSAGE );
    }

    @ProcessorTest
    void customExceptionIsThrownForSingleVehicle() {
        AbstractVehicle vehicle = new Motorcycle(); // undefine subclass

        assertThatThrownBy( () -> CustomExceptionSubclassMapper.INSTANCE.map( vehicle ) )
            .isInstanceOf( CustomSubclassMappingException.class )
            .hasMessage( EXPECTED_ERROR_MESSAGE );
    }

    @ProcessorTest
    @WithClasses({ MapperConfigSubclassMapper.class })
    void customExceptionIsThrownForMapperConfig() {
        VehicleCollection vehicles = new VehicleCollection();
        vehicles.getVehicles().add( new Car() );
        vehicles.getVehicles().add( new Motorcycle() ); // undefined subclass

        assertThatThrownBy( () -> MapperConfigSubclassMapper.INSTANCE.mapInverse( vehicles ) )
            .isInstanceOf( CustomSubclassMappingException.class )
            .hasMessage( EXPECTED_ERROR_MESSAGE );
    }

    @ProcessorTest
    @WithClasses({ MapperSubclassMapper.class })
    void customExceptionIsThrownForMapper() {
        VehicleCollection vehicles = new VehicleCollection();
        vehicles.getVehicles().add( new Car() );
        vehicles.getVehicles().add( new Motorcycle() ); // undefined subclass

        assertThatThrownBy( () -> MapperSubclassMapper.INSTANCE.mapInverse( vehicles ) )
            .isInstanceOf( CustomSubclassMappingException.class )
            .hasMessage( EXPECTED_ERROR_MESSAGE );
    }
}
