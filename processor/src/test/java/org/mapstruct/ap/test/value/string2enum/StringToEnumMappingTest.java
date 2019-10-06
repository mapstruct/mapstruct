/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.string2enum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey( "1557" )
@WithClasses({ OrderType.class, OrderMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class StringToEnumMappingTest {

    @Test
    public void testTheNormalStuff() {
        assertThat( OrderMapper.INSTANCE.mapNormal( null ) ).isNull();
        assertThat( OrderMapper.INSTANCE.mapNormal( "SPECIAL" ) ).isEqualTo( OrderType.EXTRA );
        assertThat( OrderMapper.INSTANCE.mapNormal( "DEFAULT" ) ).isEqualTo( OrderType.STANDARD );
        assertThat( OrderMapper.INSTANCE.mapNormal( "RETAIL" ) ).isEqualTo( OrderType.RETAIL );
        assertThat( OrderMapper.INSTANCE.mapNormal( "B2B" ) ).isEqualTo( OrderType.B2B );
        assertThat( OrderMapper.INSTANCE.mapNormal( "RETAIL" ) ).isEqualTo( OrderType.RETAIL );
        assertThat( OrderMapper.INSTANCE.mapNormal( "STANDARD" ) ).isEqualTo( OrderType.STANDARD );
        assertThat( OrderMapper.INSTANCE.mapNormal( "NORMAL" ) ).isEqualTo( OrderType.NORMAL );
    }

    @Test
    public void testRemainingAndNull() {
        assertThat( OrderMapper.INSTANCE.mapWithAnyUnmapped( null ) ).isEqualTo( OrderType.STANDARD );
        assertThat( OrderMapper.INSTANCE.mapWithAnyUnmapped( "DEFAULT" ) ).isNull();
        assertThat( OrderMapper.INSTANCE.mapWithAnyUnmapped( "BLAH" ) ).isEqualTo( OrderType.RETAIL );
    }

    @Test
    @WithClasses(ErroneousOrderMapperUsingAnyRemaining.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrderMapperUsingAnyRemaining.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 26,
                messageRegExp = "\"<ANY_REMAINING>\" can only be used on targets of type enum and not for " +
                    "java\\.lang\\.String\\.")
        }
    )
    public void shouldRaiseErrorWhenUsingAnyRemaining() {
    }

}
