/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1881;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1881")
@WithClasses({
    VehicleDtoMapper.class,
})
public class Issue1881Test {

    @ProcessorTest
    public void shouldCompileCorrectly() {
        VehicleDtoMapper.VehicleDto vehicle = VehicleDtoMapper.INSTANCE.map( new VehicleDtoMapper.Vehicle(
            "Test",
            100,
            "SUV"
        ) );

        assertThat( vehicle.getName() ).isEqualTo( "Test" );
        assertThat( vehicle.getVehicleProperties() ).isNotNull();
        assertThat( vehicle.getVehicleProperties().getSize() ).isEqualTo( 100 );
        assertThat( vehicle.getVehicleProperties().getType() ).isEqualTo( "SUV" );
    }
}
