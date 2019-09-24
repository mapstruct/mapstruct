/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mypackage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Andrei Arlou
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Source.class,
    Target.class,
})
public class CheckDeclaredTypeInMapperUses {

    @Test
    @WithClasses( { MapperWithNotDeclaredTypeInUses.class} )
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = MapperWithNotDeclaredTypeInUses.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                messageRegExp = "Type \"int\" cannot be used in \"@Mapper\\.uses\\(\\)\""
            )
        }
    )
    public void shouldCompilationFailedForMapperUsesNotDeclaredType() { }

    @Test
    @WithClasses( { MapperWithNotDeclaredTypeInMapperConfigUses.class, MapperConfigWithNotDecladerTypeInUses.class} )
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = MapperWithNotDeclaredTypeInMapperConfigUses.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                messageRegExp = "Type \"int\" cannot be used in \"@MapperConfig\\.uses\\(\\)\""
            )
        }
    )
    public void shouldCompilationFailedForMapperConfigUsesNotDeclaredType() { }

    @Test
    @WithClasses( { MapperWithMapperConfigNotDeclaredType.class} )
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                messageRegExp = "Mapper config class with annotation @MapperConfig cannot be \"int\""
            )
        }
    )
    public void shouldCompilationFailedIfMapperConfigIsNotDeclaredType() { }
}
