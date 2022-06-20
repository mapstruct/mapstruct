/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1159;

import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.WithServiceImplementation;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.testutil.runner.Compiler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1159")
@WithClasses({
    Issue1159Mapper.class,
})
@WithServiceImplementation(
    provides = AstModifyingAnnotationProcessor.class,
    value = FaultyAstModifyingAnnotationProcessor.class
)
public class Issue1159Test {

    @ProcessorTest(Compiler.JDK)
    // The warning is not present in the Eclipse compilation for some reason
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED, diagnostics = {
        @Diagnostic(
            kind = javax.tools.Diagnostic.Kind.WARNING,
            messageRegExp = "Failed to read AstModifyingAnnotationProcessor. Reading next processor. Reason:.*" +
                "Faulty AstModifyingAnnotationProcessor should not be instantiated"
        )
    })
    public void shouldIgnoreFaultyAstModifyingProcessor() {

        Issue1159Mapper.CarManual manual = new Issue1159Mapper.CarManual();
        manual.setContent( "test" );

        Issue1159Mapper.CarManualDto manualDto = Issue1159Mapper.INSTANCE.translateManual( manual );

        assertThat( manualDto ).isNotNull();
        assertThat( manualDto.getContent() ).isEqualTo( "test" );
    }
}
