/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.string2enum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

@IssueKey( "1557" )
@WithClasses({ OrderType.class, OrderMapper.class })
public class StringToEnumMappingTest {

    @ProcessorTest
    public void testTheNormalStuff() {
        assertThat( OrderMapper.INSTANCE.mapNormal( null ) ).isNull();
        assertThat( OrderMapper.INSTANCE.mapNormal( "SPECIAL" ) ).isEqualTo( OrderType.EXTRA );
        assertThat( OrderMapper.INSTANCE.mapNormal( "DEFAULT" ) ).isEqualTo( OrderType.STANDARD );
        assertThat( OrderMapper.INSTANCE.mapNormal( "RETAIL" ) ).isEqualTo( OrderType.RETAIL );
        assertThat( OrderMapper.INSTANCE.mapNormal( "B2B" ) ).isEqualTo( OrderType.B2B );
        assertThat( OrderMapper.INSTANCE.mapNormal( "STANDARD" ) ).isEqualTo( OrderType.STANDARD );
        assertThat( OrderMapper.INSTANCE.mapNormal( "NORMAL" ) ).isEqualTo( OrderType.NORMAL );
        assertThat( OrderMapper.INSTANCE.mapNormal( "blah" ) ).isEqualTo( OrderType.RETAIL );
    }

    @ProcessorTest
    public void testRemainingAndNull() {
        assertThat( OrderMapper.INSTANCE.mapWithAnyUnmapped( null ) ).isEqualTo( OrderType.STANDARD );
        assertThat( OrderMapper.INSTANCE.mapWithAnyUnmapped( "DEFAULT" ) ).isNull();
        assertThat( OrderMapper.INSTANCE.mapWithAnyUnmapped( "BLAH" ) ).isEqualTo( OrderType.RETAIL );
    }

    @ProcessorTest
    @WithClasses(ErroneousOrderMapperUsingNoAnyRemainingAndNoAnyUnmapped.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrderMapperUsingNoAnyRemainingAndNoAnyUnmapped.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 28,
                message = "Source = \"<ANY_REMAINING>\" or \"<ANY_UNMAPPED>\" is advisable for mapping of " +
                    "type String to an enum type." )
        }
    )
    public void shouldRaiseWarningWhenNotUsingAnyRemainingOrAnyUnmapped() {

        assertThatThrownBy( () -> ErroneousOrderMapperUsingNoAnyRemainingAndNoAnyUnmapped.INSTANCE.map( "unknown" ) )
            .isInstanceOf( IllegalArgumentException.class )
            .hasMessage( "Unexpected enum constant: unknown" );

        assertThat( ErroneousOrderMapperUsingNoAnyRemainingAndNoAnyUnmapped.INSTANCE.map( null ) )
            .isEqualTo( OrderType.STANDARD );

        assertThat( ErroneousOrderMapperUsingNoAnyRemainingAndNoAnyUnmapped.INSTANCE.map( "STANDARD" ) )
            .isNull();

        assertThat( ErroneousOrderMapperUsingNoAnyRemainingAndNoAnyUnmapped.INSTANCE.map( "RETAIL" ) )
            .isEqualTo( OrderType.RETAIL );
    }

}
