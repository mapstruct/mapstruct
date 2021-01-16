/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.errors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static javax.tools.Diagnostic.Kind.ERROR;

@IssueKey("2135")
@RunWith(AnnotationProcessorTestRunner.class)
public class QualfierMessageTest {

    @Test
    @WithClasses(ErroneousMessageByAnnotationMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = ErroneousMessageByAnnotationMapper.class,
                kind = ERROR,
                line = 19,
                message = "Qualifier error. No method found annotated with: [ @SelectMe ]. " +
                    "See https://mapstruct.org/faq/#qualifier for more info."),
            @Diagnostic(
                type = ErroneousMessageByAnnotationMapper.class,
                kind = ERROR,
                line = 19,
                messageRegExp = "Can't map property.*")

        }
    )
    public void testNoQualifyingMethodByAnnotationFound() {
    }

    @Test
    @WithClasses(ErroneousMessageByNamedMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = ErroneousMessageByNamedMapper.class,
                kind = ERROR,
                line = 19,
                message = "Qualifier error. No method found annotated with @Named#value: [ SelectMe ]. " +
                    "See https://mapstruct.org/faq/#qualifier for more info."),
            @Diagnostic(
                type = ErroneousMessageByNamedMapper.class,
                kind = ERROR,
                line = 19,
                messageRegExp = "Can't map property.*")

        }
    )
    public void testNoQualifyingMethodByNamedFound() {
    }

    @Test
    @WithClasses(ErroneousMessageByAnnotationAndNamedMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = ErroneousMessageByAnnotationAndNamedMapper.class,
                kind = ERROR,
                line = 19,
                message =
                    "Qualifier error. No method found annotated with @Named#value: [ selectMe1 and selectMe2 ], " +
                        "annotated with [ @SelectMe1 and @SelectMe2 ]. " +
                        "See https://mapstruct.org/faq/#qualifier for more info."),
            @Diagnostic(
                type = ErroneousMessageByAnnotationAndNamedMapper.class,
                kind = ERROR,
                line = 19,
                messageRegExp = "Can't map property.*")

        }
    )
    public void testNoQualifyingMethodByAnnotationAndNamedFound() {
    }

    @Test
    @WithClasses(ErroneousMessageByNamedWithIterableMapper.class)
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic(
                            type = ErroneousMessageByNamedWithIterableMapper.class,
                            kind = ERROR,
                            line = 16,
                            message = "Qualifier error. No method found annotated with @Named#value: [ SelectMe ]. " +
                                    "See https://mapstruct.org/faq/#qualifier for more info."),
                    @Diagnostic(
                            type = ErroneousMessageByNamedWithIterableMapper.class,
                            kind = ERROR,
                            line = 16,
                            messageRegExp = "Can't map property.*"),

            }
    )
    public void testNoQualifyingMethodByNamedForForgedIterableFound() {
    }

}
