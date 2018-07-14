/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.typematching;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.callbacks.typematching.CarMapper.CarDto;
import org.mapstruct.ap.test.callbacks.typematching.CarMapper.CarEntity;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Andreas Gudian
 *
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    CarMapper.class
})
public class CallbackMethodTypeMatchingTest {
    @Test
    public void callbackMethodAreCalled() {
        CarEntity carEntity = CarMapper.INSTANCE.toCarEntity( new CarDto() );

        assertThat( carEntity.getId() ).isEqualTo( 2 );
        assertThat( carEntity.getSeatCount() ).isEqualTo( 5 );
    }
}
