/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.streamtononiterable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@WithClasses({ Source.class, Target.class, SourceTargetMapper.class, StringListMapper.class })
@IssueKey("962")
@RunWith(AnnotationProcessorTestRunner.class)
public class StreamToNonIterableMappingTest {

    @Test
    public void shouldMapStringStreamToStringUsingCustomMapper() {
        Source source = new Source();
        source.setNames( Arrays.asList( "Alice", "Bob", "Jim" ).stream() );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getNames() ).isEqualTo( "Alice-Bob-Jim" );
    }

    @Test
    public void shouldReverseMapStringStreamToStringUsingCustomMapper() {
        Target target = new Target();
        target.setNames( "Alice-Bob-Jim" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getNames() ).containsExactly( "Alice", "Bob", "Jim" );
    }
}
