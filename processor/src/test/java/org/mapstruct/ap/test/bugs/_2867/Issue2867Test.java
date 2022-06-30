/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2867;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Issue2867BaseMapper.class,
    Issue2867Mapper.class,
})
@IssueKey("2867")
class Issue2867Test {

    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = @Diagnostic(
            type = Issue2867Mapper.class,
            kind = javax.tools.Diagnostic.Kind.WARNING,
            line = 14,
            message = "Unmapped target property: \"name\"." +
                " Occured at 'void update(T target, S source)' in 'Issue2867BaseMapper'."
        )
    )
    @ProcessorTest
    void shouldCompile() {

    }
}
