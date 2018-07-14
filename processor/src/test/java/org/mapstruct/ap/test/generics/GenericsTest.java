/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Andreas Gudian
 *
 */
@WithClasses({
    AbstractTo.class,
    AbstractIdHoldingTo.class,
    Source.class,
    SourceTargetMapper.class,
    TargetTo.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class GenericsTest {

    @Test
    @IssueKey("574")
    public void mapsIdCorrectly() {
        TargetTo target = new TargetTo();
        target.setId( 41L );
        assertThat( SourceTargetMapper.INSTANCE.toSource( target ).getId() ).isEqualTo( 41L );
    }
}
