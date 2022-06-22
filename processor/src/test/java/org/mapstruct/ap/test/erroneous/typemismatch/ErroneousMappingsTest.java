/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.typemismatch;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * Tests failures expected for unmappable attributes.
 *
 * @author Gunnar Morling
 */
@WithClasses({ ErroneousMapper.class, Source.class, Target.class })
public class ErroneousMappingsTest {

    @ProcessorTest
    @IssueKey("6")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 14,
                message = "Can't map property \"boolean foo\" to \"int foo\". Consider to declare/implement a "
                        + "mapping method: \"int map(boolean value)\"."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 16,
                message = "Can't map property \"int foo\" to \"boolean foo\". Consider to declare/implement a "
                        + "mapping method: \"boolean map(int value)\"."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 18,
                message = "Can't generate mapping method with primitive return type."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 20,
                message = "Can't generate mapping method with primitive parameter type."),
            @Diagnostic(type = ErroneousMapper.class,
                kind = Kind.ERROR,
                line = 22,
                message =
                    "Can't generate mapping method that has a parameter annotated with @TargetType.")
        }
    )
    public void shouldFailToGenerateMappings() {
    }
}
