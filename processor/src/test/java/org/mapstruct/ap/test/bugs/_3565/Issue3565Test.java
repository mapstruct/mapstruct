/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3565;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses(Issue3565Mapper.class)
@IssueKey("3565")
class Issue3565Test {

    @ProcessorTest
    void shouldGenerateValidCode() {
        Issue3565Mapper.Target target = Issue3565Mapper.INSTANCE.map( new Issue3565Mapper.Source( null ) );

        assertThat( target ).isNotNull();
        assertThat( target.getCondition() ).isEmpty();

        target = Issue3565Mapper.INSTANCE.map( new Issue3565Mapper.Source( false ) );

        assertThat( target ).isNotNull();
        assertThat( target.getCondition() ).hasValue( "false" );
    }
}
