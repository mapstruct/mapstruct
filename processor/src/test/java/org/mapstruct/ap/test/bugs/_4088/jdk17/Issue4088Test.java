/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4088.jdk17;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.Compiler;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("4088")
@WithClasses(Issue4088Mapper.class)
@WithJSpecify
class Issue4088Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        Issue4088Mapper.class
    );

    @ProcessorTest(Compiler.JDK)
    void shouldUpdateFromNestedRecordSource() {
        Issue4088Mapper.Target target = new Issue4088Mapper.Target();

        Issue4088Mapper.INSTANCE.update(
            new Issue4088Mapper.Request(
                new Issue4088Mapper.Request.Appointment( new Issue4088Mapper.SourceDateTime( 2026, 7, 9 ) )
            ),
            target
        );

        assertThat( target.getDate() ).isNotNull();
        assertThat( target.getDate().getYear() ).isEqualTo( 2026 );
        assertThat( target.getDate().getMonth() ).isEqualTo( 7 );
        assertThat( target.getDate().getDay() ).isEqualTo( 9 );
    }
}
