/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2213;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    NotNull.class,
    CarMapper.class,
    Car.class,
    Car2.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("2213")
public class Issue2213Test {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource()
        .addComparisonToFixtureFor( CarMapper.class );

    @Test
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
