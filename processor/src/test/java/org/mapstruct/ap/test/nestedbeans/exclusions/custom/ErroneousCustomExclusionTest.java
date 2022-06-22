/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.exclusions.custom;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.WithServiceImplementation;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Source.class,
    Target.class,
    ErroneousCustomExclusionMapper.class
})
@WithServiceImplementation( CustomMappingExclusionProvider.class )
@IssueKey("1154")
public class ErroneousCustomExclusionTest {

    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCustomExclusionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 17,
                message = "Can't map property \"Source.NestedSource nested\" to \"Target.NestedTarget nested\". " +
            "Consider to declare/implement a mapping method: \"Target.NestedTarget map(Source.NestedSource value)\".")
        }
    )
    @ProcessorTest
    public void shouldFailToCreateMappingForExcludedClass() {
    }
}
