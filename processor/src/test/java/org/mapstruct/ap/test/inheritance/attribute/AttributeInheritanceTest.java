/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance.attribute;

import static org.assertj.core.api.Assertions.assertThat;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for setting an attribute where the target attribute of a super-type.
 *
 * @author Gunnar Morling
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class AttributeInheritanceTest {

    @Test
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    public void shouldMapAttributeFromSuperType() {
        Source source = new Source();
        source.setFoo( "Bob" );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        assertThat( target.getFoo() ).isNotNull();
        assertThat( target.getFoo().toString() ).isEqualTo( "Bob" );
    }

    @Test
    @WithClasses({ Source.class, Target.class, ErroneousTargetSourceMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            type = ErroneousTargetSourceMapper.class,
            kind = Kind.ERROR,
            line = 16,
            message = "Can't map property \"java.lang.CharSequence foo\" to \"java.lang.String foo\". Consider to " +
                "declare/implement a mapping method: \"java.lang.String map(java.lang.CharSequence value)\"."
        ))
    public void shouldReportErrorDueToUnmappableAttribute() {
    }
}
