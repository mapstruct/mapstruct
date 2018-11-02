/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.propertymapping;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@IssueKey("1504")
@WithClasses( { Source.class, Target.class, UnmappableClass.class } )
@RunWith(AnnotationProcessorTestRunner.class)
public class ErroneousPropertyMappingTest {

    @Test
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

    @Test
    @WithClasses( ErroneousMapper2.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                messageRegExp = ".*Consider to declare/implement a mapping method.*") }
    )
    public void testUnmappableSourcePropertyWithNoSourceDefinedInMapping() { }

    @Test
    @WithClasses( ErroneousMapper3.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                messageRegExp = "Can't map.*constant.*" ) }
    )
    public void testUnmappableConstantAssignment() { }

    @Test
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
