/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambiguousannotatedfactorymethod;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Remo Meier
 */
@WithClasses({
    Bar.class, Foo.class, AmbiguousBarFactory.class, Source.class, SourceTargetMapperAndBarFactory.class,
    Target.class
})
public class AmbiguousAnnotatedFactoryTest {

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperAndBarFactory.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                message = "Ambiguous factory methods found for creating Bar: " +
                    "Bar createBar(Foo foo), " +
                    "Bar AmbiguousBarFactory.createBar(Foo foo)." +
                    " See https://mapstruct.org/faq/#ambiguous for more info.")

        }
    )
    public void shouldUseTwoFactoryMethods() {
    }
}


