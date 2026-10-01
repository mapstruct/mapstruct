/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3390;

import java.util.ArrayList;
import java.util.Collections;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3390")
@WithClasses(Issue3390Mapper.class)
class Issue3390Test {

    @ProcessorTest
    void shouldSetExistingCollectionToNullWhenSourceCollectionIsNull() {
        Issue3390Mapper.Target target = new Issue3390Mapper.Target(
            new ArrayList<>( Collections.singletonList( "existing" ) )
        );

        Issue3390Mapper.INSTANCE.update( target, new Issue3390Mapper.Source( null ) );

        assertThat( target.getProperty() ).isNull();
    }
}
