/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3462;

import java.util.Arrays;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("3462")
@WithClasses(Issue3462Mapper.class)
class Issue3462Test {

    @ProcessorTest
    void shouldNotTreatStreamGettersAsAlternativeSetter() {

        Issue3462Mapper.Source source = new Issue3462Mapper.Source( Arrays.asList( "first", "second" ) );
        Issue3462Mapper.Target target = Issue3462Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValues() ).containsExactly( "first", "second" );
        assertThat( target.getValuesStream() ).containsExactly( "first", "second" );

    }
}
