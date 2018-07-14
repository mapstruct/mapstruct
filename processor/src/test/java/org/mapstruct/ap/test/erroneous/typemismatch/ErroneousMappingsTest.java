/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.typemismatch;

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
 * Tests failures expected for unmappable attributes.
 *
 * @author Gunnar Morling
 */
@WithClasses({ ErroneousMapper.class, Source.class, Target.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class ErroneousMappingsTest {

    @Test
    @IssueKey("6")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 27,
                messageRegExp = "Can't map property \"boolean foo\" to \"int foo\". Consider to declare/implement a "
                        + "mapping method: \"int map\\(boolean value\\)\"."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 29,
                messageRegExp = "Can't map property \"int foo\" to \"boolean foo\". Consider to declare/implement a "
                        + "mapping method: \"boolean map\\(int value\\)\"."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 31,
                messageRegExp = "Can't generate mapping method with primitive return type\\."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 33,
                messageRegExp = "Can't generate mapping method with primitive parameter type\\."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 35,
                messageRegExp =
                    "Can't generate mapping method that has a parameter annotated with @TargetType\\.")
        }
    )
    public void shouldFailToGenerateMappings() {
    }
}
