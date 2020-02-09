/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedtarget;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests expected diagnostics for unmapped target properties.
 *
 * @author Gunnar Morling
 */
@IssueKey("35")
public class UnmappedProductTest {

    @ProcessorTest
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapper.class,
                kind = Kind.WARNING,
                line = 16,
                messageRegExp = "Unmapped target property: \"bar\""),
            @Diagnostic(type = SourceTargetMapper.class,
                kind = Kind.WARNING,
                line = 18,
                messageRegExp = "Unmapped target property: \"qux\"")
        }
    )
    public void shouldLeaveUnmappedTargetPropertyUnset() {
        Source source = new Source();
        source.setFoo( 42L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( 42L );
        assertThat( target.getBar() ).isEqualTo( 0 );
    }

    @ProcessorTest
    @WithClasses({ Source.class, Target.class, ErroneousStrictSourceTargetMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousStrictSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 17,
                messageRegExp = "Unmapped target property: \"bar\""),
            @Diagnostic(type = ErroneousStrictSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 19,
                messageRegExp = "Unmapped target property: \"qux\"")
        }
    )
    public void shouldRaiseErrorDueToUnsetTargetProperty() {
    }

    @ProcessorTest
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    @ProcessorOption(name = "mapstruct.unmappedTargetPolicy", value = "ERROR")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 16,
                messageRegExp = "Unmapped target property: \"bar\""),
            @Diagnostic(type = SourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 18,
                messageRegExp = "Unmapped target property: \"qux\"")
        }
    )
    public void shouldRaiseErrorDueToUnsetTargetPropertyWithPolicySetViaProcessorOption() {
    }
}
