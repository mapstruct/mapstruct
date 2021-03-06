/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.annotationnotfound;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * Test for (custom / external) annotation that is not on class path
 *
 * @author Sjaak Derksen
 */
@WithClasses( { Source.class, Target.class, ErroneousMapper.class } )
public class AnnotationNotFoundTest {

    @ProcessorTest
    @IssueKey( "298" )
    @ExpectedCompilationOutcome(
             value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = ErroneousMapper.class,
                        kind = Kind.ERROR,
                        line = 17,
                        messageRegExp = "NotFoundAnnotation")
            }
    )
    public void shouldFailToGenerateMappings() {
    }
}
