/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2109;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2109")
@WithClasses({
    Issue2109Mapper.class,
    Source.class,
    Target.class,
})
public class Issue2109Test {

    @ProcessorTest
    public void shouldCorrectlyMapArrayInConstructorMapping() {
        Target target = Issue2109Mapper.INSTANCE.map( new Source( 100L, new byte[] { 100, 120, 40, 40 } ) );

        assertThat( target ).isNotNull();
        assertThat( target.getId() ).isEqualTo( 100L );
        assertThat( target.getData() ).containsExactly( 100, 120, 40, 40 );

        target = Issue2109Mapper.INSTANCE.map( new Source( 50L, null ) );

        assertThat( target ).isNotNull();
        assertThat( target.getId() ).isEqualTo( 50L );
        assertThat( target.getData() ).isNull();

        target = Issue2109Mapper.INSTANCE.mapWithEmptyData( new Source( 100L, new byte[] { 100, 120, 40, 40 } ) );

        assertThat( target ).isNotNull();
        assertThat( target.getId() ).isEqualTo( 100L );
        assertThat( target.getData() ).containsExactly( 100, 120, 40, 40 );

        target = Issue2109Mapper.INSTANCE.mapWithEmptyData( new Source( 50L, null ) );

        assertThat( target ).isNotNull();
        assertThat( target.getId() ).isEqualTo( 50L );
        assertThat( target.getData() ).isEmpty();

    }
}
