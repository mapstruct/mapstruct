/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2213;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    NotNull.class,
    CarMapper.class,
    Car.class,
    Car2.class
})
@IssueKey("2213")
public class Issue2213Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource()
        .addComparisonToFixtureFor( CarMapper.class );

    @ProcessorTest
    public void testShouldNotGenerateIntermediatePrimitiveMappingMethod() {
        Car2 car = new Car2();
        int[] sourceInt = { 1, 2, 3 };
        car.setIntData( sourceInt );
        Long[] sourceLong = { 1L, 2L, 3L };
        car.setLongData( sourceLong );
        Car target = CarMapper.INSTANCE.toCar( car );

        assertThat( target ).isNotNull();
        assertThat( target.getIntData() )
            .containsExactly( 1, 2, 3 )
            .isNotSameAs( sourceInt );
        assertThat( target.getLongData() )
            .containsExactly( 1L, 2L, 3L )
            .isNotSameAs( sourceLong );
    }

}
