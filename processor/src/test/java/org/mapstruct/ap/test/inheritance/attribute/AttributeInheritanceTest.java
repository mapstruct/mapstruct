/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance.attribute;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for setting an attribute where the target attribute of a super-type.
 *
 * @author Gunnar Morling
 */
public class AttributeInheritanceTest {

    @ProcessorTest
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    public void shouldMapAttributeFromSuperType() {
        Source source = new Source();
        source.setFoo( "Bob" );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        assertThat( target.getFoo() ).isNotNull();
        assertThat( target.getFoo().toString() ).isEqualTo( "Bob" );
    }

    @ProcessorTest
    @WithClasses({ Source.class, Target.class, ErroneousTargetSourceMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            type = ErroneousTargetSourceMapper.class,
            kind = Kind.ERROR,
            line = 16,
            messageRegExp = "Can't map property \"java.lang.CharSequence foo\" to \"java.lang.String foo\""
        ))
    public void shouldReportErrorDueToUnmappableAttribute() {
    }
}
