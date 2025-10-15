/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3943;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3943")
@WithClasses(Issue3943Mapper.class)
class Issue3943Test {

    @ProcessorTest
    void shouldGenerateValidCode() {
        Issue3943Mapper.Target target = Issue3943Mapper.INSTANCE.map( 42 );
        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( 42L );
    }
}
