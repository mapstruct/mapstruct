/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedtarget.beanmapping;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

@IssueKey("3539")
public class BeanMappingIgnoreTargetTest {

    @ProcessorTest
    @WithClasses({Source.class, Target.class, BeanMappingIgnoreTargetMapper.class})
    public void shouldIgnoreTargetProperty() {
        BeanMappingIgnoreTargetMapper.INSTANCE.convert( new Source() );
    }

    @ProcessorTest
    @WithClasses({Source.class, Target.class, ErroneousBeanMappingIgnoreTargetMapper.class})
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic(type = ErroneousBeanMappingIgnoreTargetMapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 19,
                            message = "Unknown property \"unknownProperty\" in result type Target. " +
                                    "Did you mean \"name\"?")
            }
    )
    public void shouldRaiseErrorDueToUnknownProperty() {
    }
}
