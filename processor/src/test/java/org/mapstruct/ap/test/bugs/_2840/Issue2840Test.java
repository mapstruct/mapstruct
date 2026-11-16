
/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2840;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2840")
@WithClasses({
    Issue2840Mapper.class,
})
class Issue2840Test {

    @ProcessorTest
    void shouldUseMethodWithMostSpecificReturnType() {
        Issue2840Mapper.Target target = Issue2840Mapper.INSTANCE.map( (short) 10, 50 );

        assertThat( target.getShortValue() ).isEqualTo( (short) 20 );
        assertThat( target.getIntValue() ).isEqualTo( 55 );
    }
}
