/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.streamtononiterable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

@WithClasses({ Source.class, Target.class, SourceTargetMapper.class, StringListMapper.class })
@IssueKey("962")
public class StreamToNonIterableMappingTest {

    @ProcessorTest
    public void shouldMapStringStreamToStringUsingCustomMapper() {
        Source source = new Source();
        source.setNames( Stream.of( "Alice", "Bob", "Jim" ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getNames() ).isEqualTo( "Alice-Bob-Jim" );
    }

    @ProcessorTest
    public void shouldReverseMapStringStreamToStringUsingCustomMapper() {
        Target target = new Target();
        target.setNames( "Alice-Bob-Jim" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getNames() ).containsExactly( "Alice", "Bob", "Jim" );
    }
}
