/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3561;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Filip Hrisafov
 */
@WithClasses(Issue3561Mapper.class)
@IssueKey("3561")
class Issue3561Test {

    @ProcessorTest
    void shouldCorrectlyUseConditionWithContext() {

        Issue3561Mapper.Source source = new Issue3561Mapper.Source();

        assertThatThrownBy( () -> Issue3561Mapper.INSTANCE.map( source, true ) )
            .isInstanceOf( IllegalStateException.class )
            .hasMessage( "value is not initialized" );

        Issue3561Mapper.Target target = Issue3561Mapper.INSTANCE.map( source, false );
        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isNull();
    }
}
