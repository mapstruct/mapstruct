/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4106.jdk17;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.Compiler;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("4106")
@WithJSpecify
@WithClasses(Issue4106Mapper.class)
class Issue4106Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest(Compiler.JDK)
    void shouldGuardOnlyNullableReusedMethodInput() {
        generatedSource.addComparisonToFixtureFor( Issue4106Mapper.class );

        ZonedDateTime createdAt = ZonedDateTime.of( 2026, 8, 9, 12, 0, 0, 0, ZoneOffset.ofHours( 2 ) );
        Issue4106Mapper.TargetDto target = Issue4106Mapper.INSTANCE.map(
            new Issue4106Mapper.SourceEntity( createdAt, null )
        );

        assertThat( target.createdAt() ).isEqualTo(
            createdAt.withZoneSameInstant( ZoneOffset.UTC ).toOffsetDateTime()
        );
        assertThat( target.closedAt() ).isNull();
    }
}
