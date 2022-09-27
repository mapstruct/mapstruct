/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2921;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2921")
@WithClasses({
    Issue2921Mapper.class,
})
class Issue2921Test {

    @ProcessorTest
    void shouldNotUseIntegerToShortForMappingIntegerToInt() {
        Issue2921Mapper.Target target = Issue2921Mapper.INSTANCE.map( new Issue2921Mapper.Source( 10 ) );
        assertThat( target.getValue() ).isEqualTo( 10 );
    }
}
