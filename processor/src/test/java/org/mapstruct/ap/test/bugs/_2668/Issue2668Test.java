/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2668;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Ben Zegveld
 */
@IssueKey( "2668" )
class Issue2668Test {

    @ProcessorTest
    @WithClasses( Issue2668Mapper.class )
    void shouldCompileCorrectlyWithAvailableConstructors() {
    }

    @ProcessorTest
    @WithClasses( Erroneous2668ListMapper.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = Kind.ERROR,
                line = 21,
                message = "org.mapstruct.ap.test.bugs._2668.Erroneous2668ListMapper.MyArrayList"
                        + " does not have an accessible copy or no-args constructor."
                )
            }
        )
    void errorExpectedBecauseCollectionIsNotUsable() {
    }

    @ProcessorTest
    @WithClasses( Erroneous2668MapMapper.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = Kind.ERROR,
                line = 21,
                message = "org.mapstruct.ap.test.bugs._2668.Erroneous2668MapMapper.MyHashMap"
                        + " does not have an accessible copy or no-args constructor."
                )
            }
        )
    void errorExpectedBecauseMapIsNotUsable() {
    }
}
