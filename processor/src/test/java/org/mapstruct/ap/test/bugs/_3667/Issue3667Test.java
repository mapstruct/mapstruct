/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3667;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3667")
@WithClasses({
    Issue3667Mapper.class,
    Source.class,
    Target.class
})
class Issue3667Test {

    @ProcessorTest
    void shouldCorrectlyMapNestedProperty() {
        Source source = new Source(
            new Source.Nested(
                new Source.NestedNested( "value1" ),
                new Source.NestedNested( "value2" )
            )
        );

        Target target1 = Issue3667Mapper.INSTANCE.mapFirst( source );
        Target target2 = Issue3667Mapper.INSTANCE.mapSecond( source );

        assertThat( target1 ).isNotNull();
        assertThat( target1.getNested() ).isNotNull();
        assertThat( target1.getNested().getValue() ).isEqualTo( "value1" );

        assertThat( target2 ).isNotNull();
        assertThat( target2.getNested() ).isNotNull();
        assertThat( target2.getNested().getValue() ).isEqualTo( "value2" );
    }

}
