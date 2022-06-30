/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2356;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2356")
@WithClasses({
    Issue2356Mapper.class
})
public class Issue2356Test {

    @ProcessorTest
    public void shouldCompile() {
        Issue2356Mapper.Car car = new Issue2356Mapper.Car();
        car.brand = "Tesla";
        car.model = "X";
        car.modelInternational = "3";
        Issue2356Mapper.CarDTO dto = Issue2356Mapper.INSTANCE.map( car );

        assertThat( dto ).isNotNull();
        assertThat( dto.brand ).isEqualTo( "Tesla" );
        assertThat( dto.modelName ).isEqualTo( "3" );

        car = Issue2356Mapper.INSTANCE.map( dto );

        assertThat( car ).isNotNull();
        assertThat( car.brand ).isEqualTo( "Tesla" );
        assertThat( car.model ).isEqualTo( "3" );
        assertThat( car.modelInternational ).isNull();
    }
}
