/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1353;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jeffrey Smyth
 */
@IssueKey ("1353")
@WithClasses ({
    Issue1353Mapper.class,
    Source.class,
    Target.class
})
public class Issue1353Test {

    @ProcessorTest
    @ExpectedCompilationOutcome (
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic (type = Issue1353Mapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 22,
                message = "The property named \" source.string1\" has whitespaces,"
                                + " using trimmed property \"source.string1\" instead."
            ),
            @Diagnostic (type = Issue1353Mapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 22,
                message = "The property named \"string2 \" has whitespaces,"
                                + " using trimmed property \"string2\" instead."
            )
        }
    )
    public void shouldTrimArguments() {
        Source source = new Source();
        source.setString1( "TestString" );

        Target target = Issue1353Mapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getString2() ).isNotNull();
        assertThat( target.getString2() ).isEqualTo( source.getString1() );
    }
}
