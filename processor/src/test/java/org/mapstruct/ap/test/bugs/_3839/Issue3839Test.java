/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3839;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * A source consumed by {@code @Mapping( target = "." )} should count as mapped for
 * {@code unmappedSourcePolicy}, unless the target this mapping contributed nothing.
 */
@IssueKey( "3839" )
public class Issue3839Test {

    @ProcessorTest
    @WithClasses( Issue3839Mapper.class )
    public void targetThisSourceIsNotReportedAsUnmapped() {
    }

    @ProcessorTest
    @WithClasses( Issue3839DocExampleMapper.class )
    public void documentedTargetThisExampleCompiles() {
    }

    @ProcessorTest
    @WithClasses( ErroneousIssue3839UnusedTargetThisMapper.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousIssue3839UnusedTargetThisMapper.class,
                kind = Kind.WARNING,
                line = 16,
                message = "Unmapped target property: \"zulu\"."),
            @Diagnostic(type = ErroneousIssue3839UnusedTargetThisMapper.class,
                kind = Kind.ERROR,
                line = 16,
                message = "Unmapped source property: \"record\".")
        }
    )
    public void unusedTargetThisStillReportsItsSource() {
    }
}
