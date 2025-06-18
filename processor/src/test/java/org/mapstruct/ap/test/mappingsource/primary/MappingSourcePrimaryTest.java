/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingsource.primary;

import org.mapstruct.ap.test.mappingsource.primary.MappingSourcePrimaryMapper.Source1;
import org.mapstruct.ap.test.mappingsource.primary.MappingSourcePrimaryMapper.Source2;
import org.mapstruct.ap.test.mappingsource.primary.MappingSourcePrimaryMapper.Source3;
import org.mapstruct.ap.test.mappingsource.primary.MappingSourcePrimaryMapper.Target;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.Compiler;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3136")
public class MappingSourcePrimaryTest {

    // The error message is too long and causes line wrapping.
    // Eclipse has some bugs and cannot obtain the correct error message.
    @ProcessorTest(Compiler.JDK)
    @WithClasses(ErroneousMappingSourcePrimaryConflictingMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMappingSourcePrimaryConflictingMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                message = "Several possible source properties for target property \"name\"."),
            @Diagnostic(type = ErroneousMappingSourcePrimaryConflictingMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 25,
                message = "Several possible source properties for target property \"value\".")
        }
    )
    public void shouldFailOnMultiplePrimarySourcesWithConflictingProperties() {
    }

    @ProcessorTest
    @WithClasses(MappingSourcePrimaryMapper.class)
    public void shouldUsePropertyFromPrimarySourceInImplicitMapping() {
        Source1 source1 = new Source1( "source1Name", 42 );
        Source2 source2 = new Source2( "source2Name", 100L );

        Target target = MappingSourcePrimaryMapper.INSTANCE.mapWithPrimarySource( source1, source2 );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "source2Name" );
        assertThat( target.getValue() ).isEqualTo( 42 );
        assertThat( target.getCount() ).isEqualTo( 100L );
    }

    @ProcessorTest
    @WithClasses(MappingSourcePrimaryMapper.class)
    public void shouldUseNonPrimarySourceWhenPropertyIsMissingInPrimary() {
        Source1 source1 = new Source1( "source1Name", 42 );
        Source3 source3 = new Source3( 123, true );

        Target target = MappingSourcePrimaryMapper.INSTANCE.mapWithPrimaryMissingProperty( source1, source3 );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "source1Name" );
        assertThat( target.getValue() ).isEqualTo( 42 );
        assertThat( target.getId() ).isEqualTo( 123 );
        assertThat( target.getActive() ).isEqualTo( true );
    }

    @ProcessorTest
    @WithClasses(MappingSourcePrimaryMapper.class)
    public void shouldUsePropertyFromPrimarySourceInExplicitTargetOnlyMapping() {
        Source1 source1 = new Source1( "source1Name", 42 );
        Source2 source2 = new Source2( "source2Name", 100L );

        Target target = MappingSourcePrimaryMapper.INSTANCE.mapExplicitTargetWithPrimary( source1, source2 );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "source2Name" );
        assertThat( target.getValue() ).isEqualTo( 42 );
        assertThat( target.getCount() ).isEqualTo( 100L );
    }
}
