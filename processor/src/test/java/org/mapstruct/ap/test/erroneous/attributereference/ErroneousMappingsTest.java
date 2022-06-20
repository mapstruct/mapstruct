/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.attributereference;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * Test for using unknown attributes in {@code @Mapping}.
 *
 * @author Gunnar Morling
 */
@WithClasses({ Source.class, Target.class })
public class ErroneousMappingsTest {

    @ProcessorTest
    @IssueKey("11")
    @WithClasses( { ErroneousMapper.class, AnotherTarget.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 20,
                message = "Target property \"foo\" must not be mapped more than once."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 16,
                message = "No property named \"bar\" exists in source parameter(s). " +
                    "Did you mean \"foo\"?"),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 18,
                message = "Unknown property \"bar\" in result type Target. Did you mean \"foo\"?"),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 23,
                message = "No property named \"source1.foo\" exists in source parameter(s). " +
                    "Did you mean \"foo\"?"),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.WARNING,
                line = 26,
                message = "Unmapped target property: \"bar\".")
        }
    )
    public void shouldFailToGenerateMappings() {
    }

    @ProcessorTest
    @WithClasses( { ErroneousMapper1.class, DummySource.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper1.class,
                kind = Kind.ERROR,
                line = 16,
                message = "The type of parameter \"source\" has no property named \"foobar\".")
        }
    )
    public void shouldFailToGenerateMappingsErrorOnMandatoryParameterName() {
    }

    @ProcessorTest
    @WithClasses( { ErroneousMapper2.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper2.class,
                kind = Kind.ERROR,
                line = 19,
                message = "Target property \"foo\" must not be mapped more than once." )
        }
    )
    public void shouldFailToGenerateMappingsErrorOnDuplicateTarget() {
    }
}
