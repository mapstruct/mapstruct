/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3943;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3943")
@WithClasses(Issue3943Mapper.class)
class Issue3943Test {

    @ProcessorTest
    void shouldGenerateValidCodeForBeanWhenMappingImplicitly() {
        Issue3943Mapper.TargetWithMatchingProperty target = Issue3943Mapper.INSTANCE.mapImplicitly( 42 );
        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( 42L );
    }

    @ProcessorTest
    void shouldGenerateValidCodeForBeanWithMatchingProperty() {
        Issue3943Mapper.TargetWithMatchingProperty target = Issue3943Mapper.INSTANCE.mapWithMatchingProperty( 42 );
        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( 42L );
    }

    @ProcessorTest
    void shouldGenerateValidCodeForBeanWithoutMatchingProperty() {
        Issue3943Mapper.TargetWithoutMatchingProperty target =
            Issue3943Mapper.INSTANCE.mapWithoutMatchingProperty( 42 );
        assertThat( target ).isNotNull();
        assertThat( target.getNonMatchingProperty() ).isEqualTo( 42L );
    }

    @ProcessorTest
    @WithClasses( { Issue3943ErroneousMapper.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Issue3943ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 16,
                alternativeLine = 17,
                message = "Unmapped target property: \"value\"." )
        }
    )
    void shouldFailToGenerateCodeIfPropertyNameDoesNotMatch() {
    }
}
