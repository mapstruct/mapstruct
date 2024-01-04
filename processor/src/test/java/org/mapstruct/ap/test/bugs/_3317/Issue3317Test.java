/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3317;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("3317")
@WithClasses(Issue3317Mapper.class)
class Issue3317Test {

    @ProcessorTest
    void shouldGenerateValidCode() {
        Issue3317Mapper.Target target = Issue3317Mapper.INSTANCE.map( 10, 42L );
        assertThat( target ).isNotNull();
        assertThat( target.getId() ).isEqualTo( 10 );
        assertThat( target.getValue() ).isEqualTo( 42L );
    }
}
