/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4038;

import java.util.Arrays;
import java.util.LinkedHashSet;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("4038")
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
public class Issue4038Test {

    @ProcessorTest
    public void adderShouldOnlyBeUsedForMatchingProperty() {
        Source source = new Source();
        source.setName( "n" );
        source.setItems( Arrays.asList( "a", "b" ) );
        source.setMoreItems( Arrays.asList( "c", "d" ) );
        source.setExtraItems( new LinkedHashSet<String>( Arrays.asList( "e" ) ) );

        Target target = SourceTargetMapper.INSTANCE.toTarget( source );

        assertThat( target.getName() ).isEqualTo( "n" );
        assertThat( target.getItems() ).containsExactly( "a", "b" );
        assertThat( target.getMoreItems() ).containsExactly( "c", "d" );
        assertThat( target.getExtraItems() ).containsExactly( "e" );
    }
}
