/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambiguousannotatedfactorymethod;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

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
                messageRegExp = "Ambiguous factory methods found for creating "
                        + "org.mapstruct.ap.test.erroneous.ambiguousannotatedfactorymethod.Bar: "
                        + "org.mapstruct.ap.test.erroneous.ambiguousannotatedfactorymethod.Bar "
                        + "createBar\\(org.mapstruct.ap.test.erroneous.ambiguousannotatedfactorymethod.Foo foo\\), "
                        + "org.mapstruct.ap.test.erroneous.ambiguousannotatedfactorymethod.Bar "
                        + ".*AmbiguousBarFactory.createBar\\(org.mapstruct.ap.test.erroneous."
                    + "ambiguousannotatedfactorymethod.Foo foo\\)."),
            @Diagnostic(type = SourceTargetMapperAndBarFactory.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                messageRegExp = ".*\\.ambiguousannotatedfactorymethod.Bar does not have an accessible parameterless " +
                    "constructor\\.")

        }
    )
    public void shouldUseTwoFactoryMethods() {
    }
}


