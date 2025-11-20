/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3902;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * Verifies that using an unknown property in {@code @Ignored} yields a proper
 * compile error instead of an internal processor error.
 *
 * @author znight1020
 */
@WithClasses({ErroneousIssue3902Mapper.class})
@IssueKey("3902")
public class Issue3902Test {

    @ProcessorTest
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                    // Test case: mapWithOneKnownAndMultipleUnknowns
                    @Diagnostic(
                            type = ErroneousIssue3902Mapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 23,
                            message = "No property named \"foo\" exists in @Ignored for target type " +
                                    "\"ErroneousIssue3902Mapper.ZooDto\". Did you mean \"name\"?"
                    ),
                    @Diagnostic(
                            type = ErroneousIssue3902Mapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 23,
                            message = "No property named \"bar\" exists in @Ignored for target type " +
                                    "\"ErroneousIssue3902Mapper.ZooDto\". Did you mean \"name\"?"
                    ),

                    // Test case: mapWithMultipleKnownAndOneUnknown
                    @Diagnostic(
                            type = ErroneousIssue3902Mapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 26,
                            message = "No property named \"foo\" exists in @Ignored for target type " +
                                    "\"ErroneousIssue3902Mapper.ZooDto\". Did you mean \"name\"?"
                    ),

                    // Test case: mapWithTypo
                    @Diagnostic(
                            type = ErroneousIssue3902Mapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 29,
                            message = "No property named \"addres\" exists in @Ignored for target type "
                                    + "\"ErroneousIssue3902Mapper.ZooDto\". Did you mean \"address\"?"
                    )
            }
    )
    public void shouldFailOnUnknownPropertiesInIgnored() {
    }
}
