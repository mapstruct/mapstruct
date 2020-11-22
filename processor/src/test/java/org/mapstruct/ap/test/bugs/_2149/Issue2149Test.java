/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2149;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2149")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Erroneous2149Mapper.class
})
public class Issue2149Test {

    @Test
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = Erroneous2149Mapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 18,
                message = "Using @BeanMapping( ignoreByDefault = true ) with @Mapping( target = \".\", ... ) is not " +
                    "allowed. You'll need to explicitly ignore the target properties that should be ignored instead."
            ),
            @Diagnostic(
                type = Erroneous2149Mapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 20,
                message = "Unmapped target property: \"address\"."
            )
        }
    )
    public void shouldGiveCompileErrorWhenBeanMappingIgnoreByDefaultIsCombinedWithMappingTargetThis() {
    }
}
