/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.exclusions.custom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Source.class,
    Target.class,
    ErroneousCustomExclusionMapper.class
})
@WithServiceImplementation( CustomMappingExclusionProvider.class )
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1154")
public class ErroneousCustomExclusionTest {

    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCustomExclusionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 30,
                messageRegExp = "Can't map property \".*NestedSource nested\" to \".*NestedTarget nested\"\\. " +
                    "Consider to declare/implement a mapping method: \".*NestedTarget map\\(.*NestedSource value\\)" +
                    "\"\\.")
        }
    )
    @Test
    public void shouldFailToCreateMappingForExcludedClass() {
    }
}
