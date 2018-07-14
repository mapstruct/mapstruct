/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedsource;

import static org.assertj.core.api.Assertions.assertThat;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.test.unmappedtarget.Source;
import org.mapstruct.ap.test.unmappedtarget.Target;

/**
 * Tests expected diagnostics for unmapped source properties.
 *
 * @author Gunnar Morling
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class UnmappedSourceTest {

    @Test
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapper.class,
                kind = Kind.WARNING,
                line = 20,
                messageRegExp = "Unmapped source property: \"qux\""),
            @Diagnostic(type = SourceTargetMapper.class,
                kind = Kind.WARNING,
                line = 22,
                messageRegExp = "Unmapped source property: \"bar\"")
        }
    )
    public void shouldLeaveUnmappedSourcePropertyUnset() {
        Source source = new Source();
        source.setFoo( 42L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( 42L );
        assertThat( target.getBar() ).isEqualTo( 0 );
    }

    @Test
    @WithClasses({ Source.class, Target.class, ErroneousStrictSourceTargetMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousStrictSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 20,
                messageRegExp = "Unmapped source property: \"qux\""),
            @Diagnostic(type = ErroneousStrictSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 22,
                messageRegExp = "Unmapped source property: \"bar\"")
        }
    )
    public void shouldRaiseErrorDueToUnsetSourceProperty() {
    }
}
