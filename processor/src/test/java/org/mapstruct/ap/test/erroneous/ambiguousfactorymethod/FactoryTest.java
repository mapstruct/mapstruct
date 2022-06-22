/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambiguousfactorymethod;

import org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.a.BarFactory;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Sjaak Derksen
 */
@IssueKey("81")
@WithClasses({
    Bar.class, Foo.class, BarFactory.class, Source.class, SourceTargetMapperAndBarFactory.class,
    Target.class
})
public class FactoryTest {

    @ProcessorTest
    @IssueKey("81")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperAndBarFactory.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                message = "Ambiguous factory methods found for creating Bar: " +
                    "Bar createBar(), " +
                    "Bar BarFactory.createBar()." +
                    " See https://mapstruct.org/faq/#ambiguous for more info.")
        }
    )
    public void shouldUseTwoFactoryMethods() {
    }
}
