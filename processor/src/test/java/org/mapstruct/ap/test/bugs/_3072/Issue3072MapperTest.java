/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3072;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jarle Sætre
 */
@WithClasses(Issue3072Mapper.class)
@IssueKey("3072")
public class Issue3072MapperTest {

    @ProcessorTest
    @ExpectedCompilationOutcome(CompilationResult.SUCCEEDED)
    void removerOnTargetShouldBeIgnored() {
        Issue3072Mapper.Source src = new Issue3072Mapper.Source();
        src.getStrings().add( "myString" );

        Issue3072Mapper.TargetWithRemover target = Issue3072Mapper.INSTANCE.map( src );

        assertThat( target.getStrings() ).containsExactly( "myString" );
    }
}
