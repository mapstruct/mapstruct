/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1005;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1005")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    AbstractEntity.class,
    HasKey.class,
    HasPrimaryKey.class,
    Order.class,
    OrderDto.class
})
public class Issue1005Test {

    @WithClasses(Issue1005ErroneousAbstractResultTypeMapper.class)
    @Test
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Issue1005ErroneousAbstractResultTypeMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 17,
                message = "The result type AbstractEntity may not be an abstract class nor interface.")
        })
    public void shouldFailDueToAbstractResultType() {
    }

    @WithClasses(Issue1005ErroneousAbstractReturnTypeMapper.class)
    @Test
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Issue1005ErroneousAbstractReturnTypeMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "The return type AbstractEntity is an abstract class or interface. " +
                    "Provide a non abstract / non interface result type or a factory method.")
        })
    public void shouldFailDueToAbstractReturnType() {
    }

    @WithClasses(Issue1005ErroneousInterfaceResultTypeMapper.class)
    @Test
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Issue1005ErroneousInterfaceResultTypeMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 17,
                message = "The result type HasPrimaryKey may not be an abstract class nor interface.")
        })
    public void shouldFailDueToInterfaceResultType() {
    }

    @WithClasses(Issue1005ErroneousInterfaceReturnTypeMapper.class)
    @Test
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Issue1005ErroneousInterfaceReturnTypeMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "The return type HasKey is an abstract class or interface. " +
                    "Provide a non abstract / non interface result type or a factory method.")
        })
    public void shouldFailDueToInterfaceReturnType() {
    }
}
