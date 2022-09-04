/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2925;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2925")
@WithClasses({
    Issue2925Mapper.class,
    Source.class,
    Target.class,
})
class Issue2925Test {

    @ProcessorTest
    void shouldUseOptionalWrappingMethod() {
        Target target = Issue2925Mapper.INSTANCE.map( new Source( 10L ) );

        assertThat( target.getValue() )
            .hasValue( 10L );
    }
}
