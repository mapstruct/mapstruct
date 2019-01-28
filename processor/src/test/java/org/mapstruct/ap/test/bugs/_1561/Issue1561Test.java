/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1561;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sebastian Haberey
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1561")
@WithClasses({
    Issue1561Mapper.class,
    Source.class,
    Target.class,
    NestedTarget.class
})
public class Issue1561Test {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        Issue1561Mapper.class
    );

    @Test
    public void shouldCorrectlyUseAdder() {

        Source source = new Source();
        source.addProperty( "first" );
        source.addProperty( "second" );

        Target target = Issue1561Mapper.INSTANCE.map( source );

        assertThat( target.getNestedTarget().getProperties() ).containsExactly( "first", "second" );

        Source mapped = Issue1561Mapper.INSTANCE.map( target );

        assertThat( mapped.getProperties() ).containsExactly( "first", "second" );
    }
}
