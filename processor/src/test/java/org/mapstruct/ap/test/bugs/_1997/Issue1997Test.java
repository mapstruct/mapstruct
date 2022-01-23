/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1997;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Car.class,
    CarDetail.class,
    CarInsurance.class,
    CarInsuranceMapper.class
})
class Issue1997Test {

    @ProcessorTest
    void shouldCorrectCreateIntermediateObjectsWithBuilder() {
        Car source = Car.builder().model( "Model S" ).build();
        CarInsurance target = CarInsurance.builder().build();
        assertThat( target.getDetail() ).isNull();

        CarInsuranceMapper.INSTANCE.update( source, target );

        assertThat( target.getDetail() ).isNotNull();
        assertThat( target.getDetail().getModel() ).isEqualTo( "Model S" );
    }
}
