/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3310;

import java.util.Collections;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("3310")
@WithClasses(Issue3310Mapper.class)
class Issue3310Test {

    @ProcessorTest
    void shouldUseAdderWithGenericBaseClass() {
        Issue3310Mapper.Source source = new Issue3310Mapper.Source( Collections.singletonList( "test" ) );
        Issue3310Mapper.Target target = Issue3310Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getItems() ).containsExactly( "test" );
    }
}
