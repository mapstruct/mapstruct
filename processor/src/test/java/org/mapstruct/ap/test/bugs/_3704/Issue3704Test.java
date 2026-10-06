/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3704;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3704")
@WithClasses({ Source.class, Target.class, Issue3704Mapper.class })
class Issue3704Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor( Issue3704Mapper.class );

    @ProcessorTest
    void shouldMapOptionalToOptionalWithWildcardTarget() {
        Target target = Issue3704Mapper.INSTANCE.map( new Source( new Source.Nested( "value" ) ) );

        assertThat( target.getDate() ).isPresent();
        assertThat( target.getDate().get().getValue() ).isEqualTo( "value" );
    }

    @ProcessorTest
    void shouldMapEmptyOptionalToEmptyOptionalWithWildcardTarget() {
        Target target = Issue3704Mapper.INSTANCE.map( new Source( null ) );

        assertThat( target.getDate() ).isEmpty();
    }

    @ProcessorTest
    @WithClasses({ OverloadedTarget.class, Issue3704OverloadedSetterMapper.class })
    void shouldMapOptionalWithOverloadedSetterAndGenericOptionalWrapper() {
        OverloadedTarget target = Issue3704OverloadedSetterMapper.INSTANCE.map(
            new Source( new Source.Nested( "value" ) )
        );

        assertThat( target.getDate() ).isPresent();
        assertThat( target.getDate().get().getValue() ).isEqualTo( "value" );
    }
}
