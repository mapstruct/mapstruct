/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1738;

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
@IssueKey("1738")
@WithClasses({
    Issue1738Mapper.class,
    Source.class,
    Target.class
})
public class Issue1738Test {

    @Test
    public void shouldGenerateCorrectSourceNestedMapping() {
        Source source = new Source();
        Source.Nested<Number> nested = new Source.Nested<>();
        source.setNested( nested );
        nested.setValue( 100L );

        Target target = Issue1738Mapper.INSTANCE.map( source );

        assertThat( target.getValue() ).isEqualTo( 100L );
    }
}
