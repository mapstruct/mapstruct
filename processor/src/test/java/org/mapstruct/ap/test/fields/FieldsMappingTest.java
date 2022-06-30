/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.fields;

import org.assertj.core.util.Lists;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey( "557" )
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
public class FieldsMappingTest {

    @ProcessorTest
    public void shouldMapSourceToTarget() {
        Source source = new Source();
        source.normalInt = 4;
        source.normalList = Lists.newArrayList( 10, 11, 12 );
        source.fieldOnlyWithGetter = 20;

        Target target = SourceTargetMapper.INSTANCE.toTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.finalInt ).isEqualTo( "10" );
        assertThat( target.normalInt ).isEqualTo( "4" );
        assertThat( target.finalList ).containsOnly( "1", "2", "3" );
        assertThat( target.normalList ).containsOnly( "10", "11", "12" );
        assertThat( target.privateFinalList ).containsOnly( 3, 4, 5 );
        // +21 from the source getter and append 11 on the setter from the target
        assertThat( target.fieldWithMethods ).isEqualTo( "4111" );
    }

    @ProcessorTest
    public void shouldMapTargetToSource() {
        Target target = new Target();
        target.finalInt = "40";
        target.normalInt = "4";
        target.finalList = Lists.newArrayList( "2", "3" );
        target.normalList = Lists.newArrayList( "10", "11", "12" );
        target.privateFinalList = Lists.newArrayList( 10, 11, 12 );
        target.fieldWithMethods = "20";

        Source source = SourceTargetMapper.INSTANCE.toSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.finalInt ).isEqualTo( 10 );
        assertThat( source.normalInt ).isEqualTo( 4 );
        assertThat( source.finalList ).containsOnly( 1, 2, 3 );
        assertThat( source.normalList ).containsOnly( 10, 11, 12 );
        assertThat( source.getPrivateFinalList() ).containsOnly( 3, 4, 5, 10, 11, 12 );
        // 23 is appended on the target getter
        assertThat( source.fieldOnlyWithGetter ).isEqualTo( 2023 );
    }
}
