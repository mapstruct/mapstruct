/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.unmapped;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Order.class,
    OrderDto.class,
    UnmappedConstructorMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class UnmappedConstructorTest {

    @Test
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = UnmappedConstructorMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 19,
                message = "Unmapped target property: \"time\".")
        })
    public void shouldGenerateCompilableCodeForUnmappedConstructorProperties() {
        OrderDto source = new OrderDto( "truck" );

        Order target = UnmappedConstructorMapper.INSTANCE.map( source );

        assertThat( target.getName() ).isEqualTo( "truck" );
        assertThat( target.getTime() ).isNull();
    }
}
