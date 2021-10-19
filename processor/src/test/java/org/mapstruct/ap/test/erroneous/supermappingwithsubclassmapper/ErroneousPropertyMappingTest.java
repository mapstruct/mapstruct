/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.supermappingwithsubclassmapper;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

@IssueKey( "598" )
@WithClasses( { Source.class, Target.class, UnmappableClass.class, AbstractMapper.class } )
public class ErroneousPropertyMappingTest {

    @ProcessorTest
    @WithClasses( ErroneousMapper1.class )
    @IssueKey( "598" )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapper1.class,
                line = 11,
                messageRegExp = "Can't map property \"UnmappableClass property\" to \"String property\"\\. " +
                    "Consider to declare/implement a mapping method: \"String map\\(UnmappableClass value\\)\"\\. " +
                    "Occured at '(?:public abstract TARGET )?map\\(SOURCE\\) ?' in 'AbstractMapper'\\.")
        }
    )
    public void testUnmappableSourcePropertyInSuperclass() {
    }

    @ProcessorTest
    @WithClasses( ErroneousMapper2.class )
    @IssueKey( "598" )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapper2.class,
                line = 13,
                message = "Can't map property \"UnmappableClass property\" to \"String property\". " +
                    "Consider to declare/implement a mapping method: \"String map(UnmappableClass value)\".") } )
    public void testMethodOverride() {
    }
}
