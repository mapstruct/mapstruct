/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._581;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._581.source.Car;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@IssueKey( "581" )
@WithClasses({ Car.class, org.mapstruct.ap.test.bugs._581._target.Car.class, SourceTargetMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue581Test {

    @Test
    public void shouldMapSourceAndTargetWithTheSameClassName() {
        Car source = new Car();

        org.mapstruct.ap.test.bugs._581._target.Car target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( source.getFoo() );
    }
}
