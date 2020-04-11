/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.Context;
import org.mapstruct.ap.test.context.erroneous.ErroneousNodeMapperWithNonUniqueContextTypes;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

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
@RunWith(AnnotationProcessorTestRunner.class)
public class ContextParameterErroneousTest {

    @Test
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
