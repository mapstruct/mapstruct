/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1751;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1772")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Holder.class,
    Issue1751Mapper.class,
    Source.class,
    Target.class
})
public class Issue1751Test {

    @Test
    public void name() {
        Source source = new Source();
        source.setValue( "some value" );

        Holder<Target> targetHolder = Issue1751Mapper.INSTANCE.mapToHolder( source );

        assertThat( targetHolder.getValue() )
            .extracting( Target::getValue )
            .isEqualTo( "some value" );
    }
}
