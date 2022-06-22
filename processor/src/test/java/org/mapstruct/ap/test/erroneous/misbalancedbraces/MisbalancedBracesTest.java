/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.misbalancedbraces;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.DisableCheckstyle;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * Test for making sure that expressions with too many closing braces are passed through, letting the compiler raise an
 * error.
 *
 * @author Gunnar Morling
 */
@WithClasses({ MapperWithMalformedExpression.class, Source.class, Target.class })
@DisableCheckstyle
public class MisbalancedBracesTest {

    // the compiler messages due to the additional closing brace differ between JDK and Eclipse, hence we can only
    // assert on the line number but not the message
    @ProcessorTest
    @IssueKey("1056")
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = { @Diagnostic(kind = Kind.ERROR, line = 20) }
    )
    public void expressionWithMisbalancedBracesIsPassedThrough() {
    }
}
