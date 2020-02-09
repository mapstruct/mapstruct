/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.propertymapping;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

@IssueKey("1504")
@WithClasses( { Source.class, Target.class, UnmappableClass.class } )
public class ErroneousPropertyMappingTest {

    @ProcessorTest
    @WithClasses( ErroneousMapper1.class )
    @IssueKey("1504")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 16,
            messageRegExp = ".*Consider to declare/implement a mapping method.*") }
    )
    public void testUnmappableSourceProperty() { }

    @ProcessorTest
    @WithClasses( ErroneousMapper2.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                messageRegExp = ".*Consider to declare/implement a mapping method.*") }
    )
    public void testUnmappableSourcePropertyWithNoSourceDefinedInMapping() { }

    @ProcessorTest
    @WithClasses( ErroneousMapper3.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                messageRegExp = "Can't map.*constant.*" ) }
    )
    public void testUnmappableConstantAssignment() { }

    @ProcessorTest
    @WithClasses( ErroneousMapper4.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                messageRegExp = ".*Consider to declare/implement a mapping method.*") }
    )
    public void testUnmappableParameterAssignment() { }
}
