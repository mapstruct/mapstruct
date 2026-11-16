/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.javadoc;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Jose Carlos Campanero Ortiz
 */
@IssueKey("2987")
class JavadocTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses( { JavadocAnnotatedWithValueMapper.class } )
    void javadocAnnotatedWithValueMapper() {
        generatedSource.addComparisonToFixtureFor( JavadocAnnotatedWithValueMapper.class );
    }

    @ProcessorTest
    @WithClasses( { JavadocAnnotatedWithAttributesMapper.class } )
    void javadocAnnotatedWithAttributesMapper() {
        generatedSource.addComparisonToFixtureFor( JavadocAnnotatedWithAttributesMapper.class );
    }

    @ProcessorTest
    @IssueKey("2987")
    @WithClasses({ ErroneousJavadocMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousJavadocMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 15,
                message = "'value', 'authors', 'deprecated' and 'since' are undefined in @Javadoc, "
                    + "define at least one of them.")
        }
    )
    void shouldFailOnEmptyJavadocAnnotation() {
    }

}
