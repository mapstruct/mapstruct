/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._366;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@IssueKey( "366" )
@WithClasses( { Issue366Mapper.class, org.mapstruct.ap.test.bugs._366.domain.VehicleCollection.class,
    org.mapstruct.ap.test.bugs._366.domain.Vehicle.class, org.mapstruct.ap.test.bugs._366.domain.Car.class,
    org.mapstruct.ap.test.bugs._366.domain.Bike.class, org.mapstruct.ap.test.bugs._366.dto.VehicleCollection.class,
    org.mapstruct.ap.test.bugs._366.dto.Vehicle.class, org.mapstruct.ap.test.bugs._366.dto.Car.class,
    org.mapstruct.ap.test.bugs._366.dto.Bike.class, } )
public class Issue366Test {

    @ProcessorTest
    void issue366MappingTest() {
        org.mapstruct.ap.test.bugs._366.domain.VehicleCollection vehicles =
            new org.mapstruct.ap.test.bugs._366.domain.VehicleCollection();
        vehicles.getVehicles().add( new org.mapstruct.ap.test.bugs._366.domain.Car() );
        vehicles.getVehicles().add( new org.mapstruct.ap.test.bugs._366.domain.Bike() );

        org.mapstruct.ap.test.bugs._366.dto.VehicleCollection result = Issue366Mapper.INSTANCE.map( vehicles );

        assertThat( result.getVehicles() ).doesNotContainNull();
        assertThat( result.getVehicles() ) // remove generic so that test works.
                                          .extracting( vehicle -> (Class) vehicle.getClass() )
                                          .containsExactly(
                                              org.mapstruct.ap.test.bugs._366.dto.Car.class,
                                              org.mapstruct.ap.test.bugs._366.dto.Bike.class );
    }

    @ProcessorTest
    void mappingOfUnknownChildThrowsIllegalArgumentException() {
        org.mapstruct.ap.test.bugs._366.domain.VehicleCollection vehicles =
            new org.mapstruct.ap.test.bugs._366.domain.VehicleCollection();
        vehicles.getVehicles().add( new org.mapstruct.ap.test.bugs._366.domain.Car() );
        vehicles.getVehicles().add( new org.mapstruct.ap.test.bugs._366.domain.Motorcycle() );

        IllegalArgumentException thrown =
            assertThrows( IllegalArgumentException.class, () -> Issue366Mapper.INSTANCE.map( vehicles ) );

        String expectedMessage = "Not all subclasses are supported for this mapping. "
            + "Missing for class org.mapstruct.ap.test.bugs._366.domain.Motorcycle";
        assertThat( thrown.getMessage() ).isEqualTo( expectedMessage );
    }
}
