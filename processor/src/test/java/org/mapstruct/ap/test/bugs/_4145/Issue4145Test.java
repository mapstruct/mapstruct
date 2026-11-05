/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4145;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("4145")
@WithClasses({ Issue4145Mapper.class, Source.class, Target.class })
class Issue4145Test {

    @ProcessorTest
    void shouldUseAdderForExpression() {
        Source source = new Source();
        source.setName( "b" );

        Target target = Issue4145Mapper.INSTANCE.mapWithExpression( source );

        assertThat( target.getList() ).containsExactly( "a", "b" );
    }

    @ProcessorTest
    void shouldUseAdderForConstant() {
        Target target = Issue4145Mapper.INSTANCE.mapWithConstant( new Source() );

        assertThat( target.getList() ).containsExactly( "c" );
    }
}
