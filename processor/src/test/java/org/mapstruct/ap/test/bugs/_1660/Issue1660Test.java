/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1660;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1660")
@WithClasses({
    Issue1660Mapper.class,
    Source.class,
    Target.class,
})
public class Issue1660Test {

    @Test
    public void shouldNotUseStaticMethods() {
        Source source = new Source();
        source.setValue( "source" );
        Target target = Issue1660Mapper.INSTANCE.map( source );

        assertThat( target.getValue() ).isEqualTo( "source" );
        assertThat( Target.getStaticValue() ).isEqualTo( "targetStatic" );
        assertThat( Target.getOtherStaticValues() ).containsExactly( "targetOtherStatic" );
    }
}
