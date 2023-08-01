/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedsource.beanmapping;

import static org.assertj.core.api.Assertions.assertThat;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

@IssueKey("3309")
class UnmappedSourceTest {

    @ProcessorTest
    @WithClasses({ Source.class, Target.class, BeanMappingSourcePolicyMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = BeanMappingSourcePolicyMapper.class,
                kind = Kind.WARNING,
                line = 20,
                message = "Unmapped source property: \"bar\".")
        }
    )
    public void shouldCompileWithUnmappedSourcePolicySetToWarnWithBeanMapping() {
        Source source = new Source();
        Source source2 = new Source();

        source.setFoo( 10 );
        source.setBar( 20 );

        source2.setFoo( 1 );
        source2.setBar( 2 );

        Target target = BeanMappingSourcePolicyMapper.MAPPER.map( source );
        Target target2 = BeanMappingSourcePolicyMapper.MAPPER.map2( source2 );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( 10 );

        assertThat( target2 ).isNotNull();
        assertThat( target2.getFoo() ).isEqualTo( 1 );
    }

    @ProcessorTest
    @WithClasses({ Source.class, Target.class, BeanMappingSourcePolicyErroneousMapper.class })
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic(type = BeanMappingSourcePolicyErroneousMapper.class,
                    kind = Kind.ERROR,
                    line = 16,
                    message = "Unmapped source property: \"bar\".")
            }
        )
    public void shouldErrorWithUnmappedSourcePolicySetToErrorWithBeanMapping() {
    }

}
