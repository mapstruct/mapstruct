/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.contextlost;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins MapStruct's general behavior for {@code @Context} parameters in the two-step mapping of
 * {@link org.mapstruct.ap.internal.model.NullSafe2StepMappingMethod} when the outer mapping method does
 * <b>not</b> declare a {@code @Context} parameter itself:
 * <ul>
 * <li>If a plain overload of the user method is available (see
 * {@link ContextMissingOverloadMapper}), it is selected, because the overload requiring the
 * unavailable context parameter is not a candidate; the generated helper must therefore <b>not</b>
 * declare a context parameter (the context parameter is only added if the selected method needs it).
 * <li>If only the overload requiring {@code @Context} is available (see
 * {@link ErroneousContextMissingOnlyContextMapper}), it is not selected at all, because its context parameter
 * cannot be bound: the 2-step mapping cannot be generated, so resolving the explicit mapping fails with
 * an error, and no code referencing a missing context parameter is generated.
 * </ul>
 */
@WithJSpecify
@WithClasses( {
    ContextLostInput.class,
    ContextLostSourceBean.class,
    ContextLostTargetBean.class
} )
class ContextMissingNullSafe2StepTest {

    @ProcessorTest
    @WithClasses( ContextMissingOverloadMapper.class )
    void plainOverloadIsSelectedWithoutContext() {
        ContextMissingOverloadMapper mapper = ContextMissingOverloadMapper.INSTANCE;

        ContextLostSourceBean source = new ContextLostSourceBean();
        source.setInput( new ContextLostInput() );

        ContextLostTargetBean target = mapper.map( source );
        assertThat( target.getNumber() ).isEqualTo( "7" );
    }

    @ProcessorTest
    @WithClasses( ErroneousContextMissingOnlyContextMapper.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = ErroneousContextMissingOnlyContextMapper.class,
                kind = Kind.ERROR,
                line = 35,
                message = "Can't map property \"ContextLostInput input\" to \"String number\". "
                    + "Consider to declare/implement a mapping method: \"String map(ContextLostInput value)\"." )
        } )
    void onlyContextOverloadAvailableWithoutContext() {
    }
}
