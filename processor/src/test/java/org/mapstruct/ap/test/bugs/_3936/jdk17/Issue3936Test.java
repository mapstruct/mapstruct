/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3936.jdk17;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.bugs._3936.jdk17.Issue3936Mapper.From;
import org.mapstruct.ap.test.bugs._3936.jdk17.Issue3936Mapper.To;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.Compiler;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for issue 3936: when the source type is a sealed hierarchy that is exhaustively covered by
 * {@code @SubclassMapping} and every branch maps to the same concrete target, the {@code else} branch must honour the
 * {@code subclassExhaustiveStrategy} (throw the configured exception) instead of constructing the target with default
 * (possibly invalid) values.
 * <p>
 * Because the source hierarchy is sealed and exhaustively covered, the {@code else} branch is unreachable, so the
 * generated exhaustive-strategy guard is verified through the generated-source fixture rather than at runtime.
 *
 * @author henriquejsza
 */
@IssueKey("3936")
@WithClasses(Issue3936Mapper.class)
class Issue3936Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor( Issue3936Mapper.class );

    @ProcessorTest(Compiler.JDK)
    void shouldMapCoveredSubclasses() {
        assertThat( Issue3936Mapper.INSTANCE.map( new From.From1( 42 ) ) ).isEqualTo( new To( 42 ) );
        assertThat( Issue3936Mapper.INSTANCE.map( new From.From2( 7 ) ) ).isEqualTo( new To( 7 ) );
        assertThat( Issue3936Mapper.INSTANCE.map( null ) ).isNull();
    }
}
