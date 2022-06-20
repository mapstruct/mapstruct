/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1648;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1648")
@WithClasses({
    Issue1648Mapper.class,
    Source.class,
    Target.class,
})
public class Issue1648Test {

    @ProcessorTest
    public void shouldCorrectlyMarkSourceAsUsed() {
        Source source = new Source();
        source.setSourceValue( "value" );

        Target target = Issue1648Mapper.INSTANCE.map( source );

        assertThat( target.getTargetValue() ).isEqualTo( "value" );
    }
}
