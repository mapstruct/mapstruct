/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.iterabletononiterable;

import java.util.Arrays;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({ Source.class, Target.class, SourceTargetMapper.class, StringListMapper.class })
public class IterableToNonIterableMappingTest {

    @ProcessorTest
    @IssueKey("6")
    public void shouldMapStringListToStringUsingCustomMapper() {
        Source source = new Source();
        source.setNames( Arrays.asList( "Alice", "Bob", "Jim" ) );
        source.publicNames = Arrays.asList( "Alice", "Bob", "Jim" );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getNames() ).isEqualTo( "Alice-Bob-Jim" );
        assertThat( target.publicNames ).isEqualTo( "Alice-Bob-Jim" );
    }

    @ProcessorTest
    @IssueKey("6")
    public void shouldReverseMapStringListToStringUsingCustomMapper() {
        Target target = new Target();
        target.setNames( "Alice-Bob-Jim" );
        target.publicNames = "Alice-Bob-Jim";

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getNames() ).isEqualTo( Arrays.asList( "Alice", "Bob", "Jim" ) );
        assertThat( source.publicNames ).isEqualTo( Arrays.asList( "Alice", "Bob", "Jim" ) );
    }
}
