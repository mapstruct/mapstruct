/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1457;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    SourceBook.class,
    TargetBook.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1457")
public class Issue1457Test {

    private SourceBook sourceBook;
    private String authorFirstName;
    private String authorLastName;

    @Before
    public void setup() {
        sourceBook = new SourceBook();
        sourceBook.setIsbn( "3453146972" );
        sourceBook.setTitle( "Per Anhalter durch die Galaxis" );

        authorFirstName = "Douglas";
        authorLastName = "Adams";
    }


    @Test
    @WithClasses({
        BookMapper.class
    })
    public void testMapperWithMatchingParameterNames() {
        TargetBook targetBook = BookMapper.INSTANCE.mapBook( sourceBook, authorFirstName, authorLastName );

        assertTargetBookMatchesSourceBook(targetBook);
    }

    @Test
    @WithClasses({
        DifferentOrderingBookMapper.class
    })
    public void testMapperWithMatchingParameterNamesAndDifferentOrdering() {
        TargetBook targetBook = DifferentOrderingBookMapper.INSTANCE.mapBook( sourceBook, authorFirstName, authorLastName );

        assertTargetBookMatchesSourceBook(targetBook);
    }

    private void assertTargetBookMatchesSourceBook(TargetBook targetBook) {
        assertThat( sourceBook.getIsbn() ).isEqualTo( targetBook.getIsbn() );
        assertThat( sourceBook.getTitle() ).isEqualTo( targetBook.getTitle() );
        assertThat( authorFirstName ).isEqualTo( targetBook.getAuthorFirstName() );
        assertThat( authorLastName ).isEqualTo( targetBook.getAuthorLastName() );
    }

    @Test
    @WithClasses({
        ErroneousBookMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = @Diagnostic(
            messageRegExp =
                "Lifecycle method has multiple matching parameters \\(e\\. g\\. same type\\), in this case " +
                    "please ensure to name the parameters in the lifecycle and mapping method identical\\. This " +
                    "lifecycle method will not be used for the mapping method '.*\\.TargetBook mapBook\\(" +
                    ".*\\.SourceBook sourceBook, .*\\.String authorFirstName, .*\\.String authorLastName\\)'\\.",
            kind = javax.tools.Diagnostic.Kind.WARNING,
            line = 21
        )
    )
    public void testMapperWithoutMatchingParameterNames() {

    }
}
