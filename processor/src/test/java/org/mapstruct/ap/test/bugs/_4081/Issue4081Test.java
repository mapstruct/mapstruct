/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4081;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * Reproducer for <a href="https://github.com/mapstruct/mapstruct/issues/4081">4081</a>.
 *
 * @author Agustin Ranieri
 */
@IssueKey( "4081" )
@WithJSpecify
public class Issue4081Test {

    @ProcessorTest
    @WithClasses( { ErroneousIssue4081Mapper.class, Source.class, Target.class } )
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousIssue4081Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                message = "Can't map potentially nullable source property \"payload\" to @NonNull " +
                    "constructor parameter \"payload\". Consider adding a defaultValue or " +
                    "defaultExpression.")
        })
    public void nullableMethodReturnToNonNullConstructorParamShouldFail() {
    }
}
