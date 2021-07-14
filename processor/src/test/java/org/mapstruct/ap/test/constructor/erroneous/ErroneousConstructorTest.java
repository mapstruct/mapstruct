/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.erroneous;

import org.mapstruct.ap.test.constructor.ConstructorProperties;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.Compiler;

/**
 * @author Filip Hrisafov
 */
public class ErroneousConstructorTest {

    @ProcessorTest(Compiler.JDK)
    @WithClasses({
        ErroneousAmbiguousConstructorsMapper.class,
        PersonDto.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic(
            type = ErroneousAmbiguousConstructorsMapper.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 17,
            message = "Ambiguous constructors found for creating org.mapstruct.ap.test.constructor.erroneous" +
                ".ErroneousAmbiguousConstructorsMapper.PersonWithMultipleConstructors: " +
                "PersonWithMultipleConstructors(java.lang.String), " +
                "PersonWithMultipleConstructors(java.lang.String,int). Either declare parameterless constructor " +
                "or annotate the default constructor with an annotation named @Default."
        )
    })
    public void shouldUseMultipleConstructorsCompilerJdk() {
    }

    @ProcessorTest(Compiler.ECLIPSE)
    @WithClasses({
        ErroneousAmbiguousConstructorsMapper.class,
        PersonDto.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic(
            type = ErroneousAmbiguousConstructorsMapper.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 17,
            message = "Ambiguous constructors found for creating org.mapstruct.ap.test.constructor.erroneous" +
                ".ErroneousAmbiguousConstructorsMapper.PersonWithMultipleConstructors: " +
                "public void <init>(java.lang.String) , public void <init>(java.lang.String, int) . Either declare " +
                "parameterless constructor or annotate the default constructor with an annotation named @Default."
        )
    })
    public void shouldUseMultipleConstructorsCompilerEclipse() {
    }

    @ProcessorTest
    @WithClasses({
        ConstructorProperties.class,
        ErroneousConstructorPropertiesMapper.class,
        PersonDto.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic(
            type = ErroneousConstructorPropertiesMapper.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 18,
            message = "Incorrect @ConstructorProperties for org.mapstruct.ap.test.constructor.erroneous" +
                ".ErroneousConstructorPropertiesMapper.PersonWithIncorrectConstructorProperties. The size of the " +
                "@ConstructorProperties does not match the number of constructor parameters")
    })
    public void shouldNotCompileIfConstructorPropertiesDoesNotMatch() {
    }
}
