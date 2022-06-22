/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1681;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1681")
@WithClasses({
    Issue1681Mapper.class,
    Source.class,
    Target.class
})
public class Issue1681Test {

    @ProcessorTest
    public void shouldCompile() {
        Target target = new Target( "before" );
        Source source = new Source();
        source.setValue( "after" );

        Target updatedTarget = Issue1681Mapper.INSTANCE.update( target, source );

        assertThat( updatedTarget ).isSameAs( target );
        assertThat( updatedTarget.getValue() ).isEqualTo( "after" );
    }

    @ProcessorTest
    public void shouldCompileWithBuilder() {
        Target.Builder targetBuilder = Target.builder();
        targetBuilder.builderValue( "before" );
        Source source = new Source();
        source.setValue( "after" );

        Target updatedTarget = Issue1681Mapper.INSTANCE.update( targetBuilder, source );

        assertThat( updatedTarget ).isNotNull();
        assertThat( updatedTarget.getValue() ).isEqualTo( "after" );
        assertThat( targetBuilder.getBuilderValue() ).isEqualTo( "after" );
    }
}
