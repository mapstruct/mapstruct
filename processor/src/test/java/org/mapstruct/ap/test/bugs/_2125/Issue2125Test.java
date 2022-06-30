/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2125;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("2125")
@WithClasses({
    Comment.class,
    Repository.class,
})
public class Issue2125Test {

    @ProcessorTest
    @WithClasses({
        Issue2125Mapper.class
    })
    public void shouldSelectProperMethod() {

        Comment comment = Issue2125Mapper.INSTANCE.clone(
            new Comment( 2125, "Fix issue" ),
            1000
        );

        assertThat( comment ).isNotNull();
        assertThat( comment.getIssueId() ).isEqualTo( 2125 );

        comment = Issue2125Mapper.INSTANCE.cloneWithQualifier(
            new Comment( 2125, "Fix issue" ),
            1000
        );

        assertThat( comment ).isNotNull();
        assertThat( comment.getIssueId() ).isEqualTo( 2126 );

        comment = Issue2125Mapper.INSTANCE.cloneWithQualifierExplicitSource(
            new Comment( 2125, "Fix issue" ),
            1000
        );

        assertThat( comment ).isNotNull();
        assertThat( comment.getIssueId() ).isEqualTo( 1001 );
    }

    @ProcessorTest
    @WithClasses({
        Issue2125ErroneousMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Issue2125ErroneousMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 15,
                alternativeLine = 17, // For some reason javac reports the error on the method instead of the annotation
                message = "The type of parameter \"repository\" has no property named \"issueId\". Please define the " +
                    "source property explicitly."),
        })
    public void shouldReportErrorWhenMultipleSourcesMatch() {
    }
}
