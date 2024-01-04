/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3238;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Filip Hrisafov
 */
@WithClasses(ErroneousIssue3238Mapper.class)
@IssueKey("3238")
class Issue3238Test {

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = @Diagnostic( type = ErroneousIssue3238Mapper.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 14,
            message = "Using @Mapping( target = \".\", ignore = true ) is not allowed." +
                " You need to use @BeanMapping( ignoreByDefault = true ) if you would like to ignore" +
                " all non explicitly mapped target properties."
        )
    )
    void shouldGenerateValidCompileError() {

    }

}
