/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._131;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@IssueKey( "131" )
@WithClasses( { Issue131Mapper.class, org.mapstruct.ap.test.bugs._131.domain.VehicleCollection.class,
    org.mapstruct.ap.test.bugs._131.domain.Vehicle.class, org.mapstruct.ap.test.bugs._131.domain.Car.class,
    org.mapstruct.ap.test.bugs._131.domain.Bike.class, org.mapstruct.ap.test.bugs._131.dto.VehicleCollection.class,
    org.mapstruct.ap.test.bugs._131.dto.Vehicle.class, org.mapstruct.ap.test.bugs._131.dto.Car.class,
    org.mapstruct.ap.test.bugs._131.dto.Bike.class, } )
public class Issue131Test {

    @ProcessorTest
    void issue131MappingTest() {
        org.mapstruct.ap.test.bugs._131.domain.VehicleCollection vehicles =
            new org.mapstruct.ap.test.bugs._131.domain.VehicleCollection();
        vehicles.getVehicles().add( new org.mapstruct.ap.test.bugs._131.domain.Car() );
        vehicles.getVehicles().add( new org.mapstruct.ap.test.bugs._131.domain.Bike() );

        org.mapstruct.ap.test.bugs._131.dto.VehicleCollection result = Issue131Mapper.INSTANCE.map( vehicles );

        assertThat( result.getVehicles() ).doesNotContainNull();
        assertThat( result.getVehicles() ) // remove generic so that test works.
                                          .extracting( vehicle -> (Class) vehicle.getClass() )
                                          .containsExactly(
                                              org.mapstruct.ap.test.bugs._131.dto.Car.class,
                                              org.mapstruct.ap.test.bugs._131.dto.Bike.class );
    }
}
