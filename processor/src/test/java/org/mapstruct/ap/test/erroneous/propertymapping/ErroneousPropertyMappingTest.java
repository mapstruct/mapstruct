/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.propertymapping;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

@IssueKey("1504")
@WithClasses({ Source.class, Target.class, UnmappableClass.class })
public class ErroneousPropertyMappingTest {

    @ProcessorTest
    @WithClasses(ErroneousMapper1.class)
    @IssueKey("1504")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"UnmappableClass source\" to \"String property\". " +
                    "Consider to declare/implement a mapping method: \"String map(UnmappableClass value)\".")
        }
    )
    public void testUnmappableSourceProperty() {
    }

    @ProcessorTest
    @WithClasses(ErroneousMapper2.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"UnmappableClass nameBasedSource\" to \"String nameBasedSource\"." +
                    " Consider to declare/implement a mapping method: \"String map(UnmappableClass value)\".")
        }
    )
    public void testUnmappableSourcePropertyWithNoSourceDefinedInMapping() {
    }

    @ProcessorTest
    @WithClasses(ErroneousMapper3.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map \"constant\" to \"UnmappableClass constant\".")
        }
    )
    public void testUnmappableConstantAssignment() {
    }

    @ProcessorTest
    @WithClasses(ErroneousMapper4.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"UnmappableClass source\" to \"String property\". " +
                    "Consider to declare/implement a mapping method: \"String map(UnmappableClass value)\".")
        }
    )
    public void testUnmappableParameterAssignment() {
    }
}
