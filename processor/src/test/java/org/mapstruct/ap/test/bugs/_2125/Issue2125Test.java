/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2125;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("2125")
@WithClasses({
    Comment.class,
    Issue.class,
    Repository.class,
})
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue2125Test {

    @Test
    @WithClasses({
        Issue2125Mapper.class
    })
    public void shouldSelectProperMethod() {

        Comment comment = Issue2125Mapper.INSTANCE.clone(
            new Comment( 2125, "Fix issue" ),
            new Repository( "mapstruct", "mapstruct" )
        );

        assertThat( comment ).isNotNull();
        assertThat( comment.getIssueId() ).isEqualTo( 2125 );

        comment = Issue2125Mapper.INSTANCE.cloneWithQualifier(
            new Comment( 2125, "Fix issue" ),
            new Repository( "mapstruct", "mapstruct" )
        );

        assertThat( comment ).isNotNull();
        assertThat( comment.getIssueId() ).isEqualTo( 2126 );
    }

    @Test
    @WithClasses({
        Issue2125ErroneousMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Issue2125ErroneousMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 15,
                message = "Several possible source properties for target property \"issueId\".")
        })
    public void shouldReportErrorWhenMultipleSourcesMatch() {
    }
}
