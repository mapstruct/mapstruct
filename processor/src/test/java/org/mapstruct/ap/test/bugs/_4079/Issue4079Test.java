/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4079;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@IssueKey( "4079" )
@WithJSpecify
public class Issue4079Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses( { Issue4079Mapper.class, Source.class, Target.class } )
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Issue4079Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                message = "Can't map potentially nullable source property \"nested\" to @NonNull " +
                    "constructor parameter \"nested\". Consider adding a defaultValue or " +
                    "defaultExpression.")
        })
    public void nullableSourceToNonNullConstructorParamUnderNullMarkedShouldFail() {
    }
}
