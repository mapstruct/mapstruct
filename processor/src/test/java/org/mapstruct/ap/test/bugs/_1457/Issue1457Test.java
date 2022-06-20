/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1457;

import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    SourceBook.class,
    TargetBook.class
})
@IssueKey("1457")
public class Issue1457Test {

    private SourceBook sourceBook;
    private String authorFirstName;
    private String authorLastName;

    @BeforeEach
    public void setup() {
        sourceBook = new SourceBook();
        sourceBook.setIsbn( "3453146972" );
        sourceBook.setTitle( "Per Anhalter durch die Galaxis" );

        authorFirstName = "Douglas";
        authorLastName = "Adams";
    }

    @ProcessorTest
    @WithClasses({
        BookMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = @Diagnostic(
            message =
                "Lifecycle method has multiple matching parameters (e. g. same type), in this case please ensure to " +
                    "name the parameters in the lifecycle and mapping method identical. This lifecycle method will " +
                    "not be used for the mapping method 'org.mapstruct.ap.test.bugs._1457.TargetBook mapBook(org" +
                    ".mapstruct.ap.test.bugs._1457.SourceBook sourceBook, java.lang.String authorFirstName, java.lang" +
                    ".String authorLastName)'.",
            kind = javax.tools.Diagnostic.Kind.WARNING,
            line = 43
        )
    )
    public void testMapperWithMatchingParameterNames() {
        TargetBook targetBook = BookMapper.INSTANCE.mapBook( sourceBook, authorFirstName, authorLastName );

        assertTargetBookMatchesSourceBook( targetBook );

        assertThat( targetBook.isAfterMappingWithoutAuthorName() ).isTrue();
        assertThat( targetBook.getAfterMappingWithOnlyFirstName() ).isEqualTo( authorFirstName );
        assertThat( targetBook.getAfterMappingWithOnlyLastName() ).isEqualTo( authorLastName );
        assertThat( targetBook.isAfterMappingWithDifferentVariableName() ).isFalse();
    }

    @ProcessorTest
    @WithClasses({
        DifferentOrderingBookMapper.class
    })
    public void testMapperWithMatchingParameterNamesAndDifferentOrdering() {
        TargetBook targetBook = DifferentOrderingBookMapper.INSTANCE.mapBook(
            sourceBook,
            authorFirstName,
            authorLastName
        );

        assertTargetBookMatchesSourceBook( targetBook );
    }

    @ProcessorTest
    @WithClasses({
        ObjectFactoryBookMapper.class
    })
    public void testMapperWithObjectFactory() {
        TargetBook targetBook = ObjectFactoryBookMapper.INSTANCE.mapBook(
            sourceBook,
            authorFirstName,
            authorLastName
        );

        assertTargetBookMatchesSourceBook( targetBook );
    }

    private void assertTargetBookMatchesSourceBook(TargetBook targetBook) {
        assertThat( sourceBook.getIsbn() ).isEqualTo( targetBook.getIsbn() );
        assertThat( sourceBook.getTitle() ).isEqualTo( targetBook.getTitle() );
        assertThat( authorFirstName ).isEqualTo( targetBook.getAuthorFirstName() );
        assertThat( authorLastName ).isEqualTo( targetBook.getAuthorLastName() );
    }

    @ProcessorTest
    @WithClasses({
        ErroneousBookMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = @Diagnostic(
            message =
                "Lifecycle method has multiple matching parameters (e. g. same type), in this case please ensure to " +
                    "name the parameters in the lifecycle and mapping method identical. This lifecycle method will " +
                    "not be used for the mapping method 'org.mapstruct.ap.test.bugs._1457.TargetBook mapBook(org" +
                    ".mapstruct.ap.test.bugs._1457.SourceBook sourceBook, java.lang.String authorFirstName, java.lang" +
                    ".String authorLastName)'.",
            kind = javax.tools.Diagnostic.Kind.WARNING,
            line = 22
        )
    )
    public void testMapperWithoutMatchingParameterNames() {
    }
}
