/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2263;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2263")
@WithClasses({
    Erroneous2263Mapper.class,
    Source.class,
    Target.class,
})
public class Issue2263Test {

    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Erroneous2263Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"Object value\" to \"String value\". " +
                    "Consider to declare/implement a mapping method: \"String map(Object value)\".")
        })
    public void shouldCorrectlyReportUnmappableTargetObject() {

    }
}
