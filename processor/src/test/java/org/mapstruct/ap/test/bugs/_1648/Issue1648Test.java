/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1648;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1648")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Issue1648Mapper.class,
    Source.class,
    Target.class,
})
public class Issue1648Test {

    @Test
    public void shouldCorrectlyMarkSourceAsUsed() {
        Source source = new Source();
        source.setSourceValue( "value" );

        Target target = Issue1648Mapper.INSTANCE.map( source );

        assertThat( target.getTargetValue() ).isEqualTo( "value" );
    }
}
