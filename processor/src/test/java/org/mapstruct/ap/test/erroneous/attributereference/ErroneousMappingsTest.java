/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.attributereference;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for using unknown attributes in {@code @Mapping}.
 *
 * @author Gunnar Morling
 */
@WithClasses({ Source.class, Target.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class ErroneousMappingsTest {

    @Test
    @IssueKey("11")
    @WithClasses( { ErroneousMapper.class, AnotherTarget.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 20,
                messageRegExp = "Target property \"foo\" must not be mapped more than once"),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 16,
                messageRegExp = "No property named \"bar\" exists in source parameter\\(s\\)\\. " +
                    "Did you mean \"foo\"?"),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 18,
                messageRegExp = "Unknown property \"bar\" in result type " +
                    "org.mapstruct.ap.test.erroneous.attributereference.Target. Did you mean \"foo\"?"),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 23,
                messageRegExp = "No property named \"source1.foo\" exists in source parameter\\(s\\)\\. " +
                    "Did you mean \"foo\"?"),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.WARNING,
                line = 26,
                messageRegExp = "Unmapped target property: \"bar\"")
        }
    )
    public void shouldFailToGenerateMappings() {
    }

    @Test
    @WithClasses( { ErroneousMapper1.class, DummySource.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper1.class,
                kind = Kind.ERROR,
                line = 16,
                messageRegExp = "The type of parameter \"source\" has no property named \"foobar\"")
        }
    )
    public void shouldFailToGenerateMappingsErrorOnMandatoryParameterName() {
    }

    @Test
    @WithClasses( { ErroneousMapper2.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper2.class,
                kind = Kind.ERROR,
                line = 19,
                messageRegExp = "Target property \"foo\" must not be mapped more than once" )
        }
    )
    public void shouldFailToGenerateMappingsErrorOnDuplicateTarget() {
    }
}
