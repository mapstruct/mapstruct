/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3153;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses(Issue3153Mapper.class)
@IssueKey("3153")
class Issue3153Test {

    @ProcessorTest
    void shouldNotTrimStringValueSource() {
        assertThat( Issue3153Mapper.INSTANCE.mapToEnum( "PR" ) ).isEqualTo( Issue3153Mapper.Target.PR );
        assertThat( Issue3153Mapper.INSTANCE.mapToEnum( " PR" ) ).isEqualTo( Issue3153Mapper.Target.PR );
        assertThat( Issue3153Mapper.INSTANCE.mapToEnum( "  PR" ) ).isEqualTo( Issue3153Mapper.Target.PR );
        assertThat( Issue3153Mapper.INSTANCE.mapToEnum( "   PR" ) ).isEqualTo( Issue3153Mapper.Target.PR );

        assertThat( Issue3153Mapper.INSTANCE.mapFromEnum( Issue3153Mapper.Target.PR ) ).isEqualTo( "   PR" );
    }
}
