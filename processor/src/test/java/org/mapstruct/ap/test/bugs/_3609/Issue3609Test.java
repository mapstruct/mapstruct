/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3609;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Roman Obolonskyii
 */
@WithClasses(Issue3609Mapper.class)
@IssueKey("3609")
public class Issue3609Test {

    @ProcessorTest
    void shouldGenerateValidCode() {
        Issue3609Mapper.VehicleDto target =
            Issue3609Mapper.INSTANCE.toVehicleDto( new Issue3609Mapper.Car( 1, 1 ) );

        assertThat( target ).isNotNull();
    }
}
