/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambiguousfactorymethod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.a.BarFactory;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Sjaak Derksen
 */
@IssueKey("81")
@WithClasses({
    Bar.class, Foo.class, BarFactory.class, Source.class, SourceTargetMapperAndBarFactory.class,
    Target.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class FactoryTest {

    @Test
    @IssueKey("81")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperAndBarFactory.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                message = "Ambiguous factory methods found for creating org.mapstruct.ap.test.erroneous" +
                    ".ambiguousfactorymethod.Bar: org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.Bar " +
                    "createBar(), org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.Bar org.mapstruct.ap.test" +
                    ".erroneous.ambiguousfactorymethod.a.BarFactory.createBar()." +
                    " See https://mapstruct.org/faq/#ambiguous for more info.")
        }
    )
    public void shouldUseTwoFactoryMethods() {
    }
}
