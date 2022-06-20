/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.Context;
import org.mapstruct.ap.test.context.erroneous.ErroneousNodeMapperWithNonUniqueContextTypes;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * Tests the erroneous usage of the {@link Context} annotation in the following situations:
 * <ul>
 * <li>using the the same context parameter type twice in the same method
 * </ul>
 *
 * @author Andreas Gudian
 */
@IssueKey("975")
@WithClasses({
    Node.class,
    NodeDto.class,
    CycleContext.class })
public class ContextParameterErroneousTest {

    @ProcessorTest
    @WithClasses(ErroneousNodeMapperWithNonUniqueContextTypes.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            kind = Kind.ERROR,
            line = 20,
            type = ErroneousNodeMapperWithNonUniqueContextTypes.class,
            message = "The types of @Context parameters must be unique."))
    public void reportsNonUniqueContextParamType() {
    }
}
