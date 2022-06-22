/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1561;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Sebastian Haberey
 */
@IssueKey("1561")
@WithClasses({
    Issue1561Mapper.class,
    Source.class,
    Target.class,
    NestedTarget.class
})
public class Issue1561Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        Issue1561Mapper.class
    );

    @ProcessorTest
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
