/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambiguousfactorymethod;

import org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.a.BarFactory;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

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
                messageRegExp = "Ambiguous factory methods found for creating "
                        + "org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.Bar: "
                        + "org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.Bar createBar\\(\\), "
                        + "org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.Bar .*BarFactory.createBar\\(\\)."),
            @Diagnostic(type = SourceTargetMapperAndBarFactory.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                messageRegExp = ".*\\.ambiguousfactorymethod\\.Bar does not have an accessible parameterless "
                    + "constructor\\.")

        }
    )
    public void shouldUseTwoFactoryMethods() {
    }
}
