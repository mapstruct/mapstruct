/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1904;

import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;
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
@IssueKey("1904")
@WithClasses({
    Issue1904Mapper.class,
})
@WithServiceImplementation(
    provides = AstModifyingAnnotationProcessor.class,
    value = AstModifyingAnnotationProcessorSaysNo.class
)
public class Issue1904Test {

    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic(
            type = Issue1904Mapper.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 14,
            message = "No implementation was created for Issue1904Mapper due to having a problem in the erroneous " +
                "element org.mapstruct.ap.test.bugs._1904.Issue1904Mapper.CarManualDto. Hint: this often means that " +
                "some other annotation processor was supposed to process the erroneous element. You can also enable " +
                "MapStruct verbose mode by setting -Amapstruct.verbose=true as a compilation argument."
        )
    })
    public void shouldHaveCompilationErrorIfMapperCouldNotBeCreated() {
    }
}
