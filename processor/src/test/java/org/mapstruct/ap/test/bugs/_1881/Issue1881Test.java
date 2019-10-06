/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1881;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1881")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    VehicleDtoMapper.class,
})
public class Issue1881Test {

    @Test
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
