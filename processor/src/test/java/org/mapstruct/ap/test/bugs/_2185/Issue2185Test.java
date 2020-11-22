/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2185;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2185")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    TodoMapper.class
})
public class Issue2185Test {

    @Test
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = TodoMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 14,
                message = "The mapper org.mapstruct.ap.test.bugs._2185.TodoMapper is referenced itself in Mapper#uses.")
        }
    )
    public void shouldCompile() {

        TodoMapper.TodoResponse response = TodoMapper.INSTANCE.toResponse( new TodoMapper.TodoEntity( "test" ) );

        assertThat( response ).isNotNull();
        assertThat( response.getNote() ).isEqualTo( "test" );
    }
}
