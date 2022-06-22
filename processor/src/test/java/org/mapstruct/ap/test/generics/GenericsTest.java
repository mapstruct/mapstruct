/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

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
public class GenericsTest {

    @ProcessorTest
    @IssueKey("574")
    public void mapsIdCorrectly() {
        TargetTo target = new TargetTo();
        target.setId( 41L );
        assertThat( SourceTargetMapper.INSTANCE.toSource( target ).getId() ).isEqualTo( 41L );
    }
}
