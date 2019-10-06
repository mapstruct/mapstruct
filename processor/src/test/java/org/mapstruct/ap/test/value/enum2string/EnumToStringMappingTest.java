/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.enum2string;

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
public class EnumToStringMappingTest {

    @Test
    public void testTheNormalStuff() {
        assertThat( OrderMapper.INSTANCE.mapNormal( null ) ).isNull();
        assertThat( OrderMapper.INSTANCE.mapNormal( OrderType.EXTRA ) ).isEqualTo( "SPECIAL" );
        assertThat( OrderMapper.INSTANCE.mapNormal( OrderType.STANDARD ) ).isEqualTo( "DEFAULT" );
        assertThat( OrderMapper.INSTANCE.mapNormal( OrderType.NORMAL ) ).isEqualTo( "DEFAULT" );
        assertThat( OrderMapper.INSTANCE.mapNormal( OrderType.B2B ) ).isEqualTo( "B2B" );
        assertThat( OrderMapper.INSTANCE.mapNormal( OrderType.RETAIL ) ).isEqualTo( "RETAIL" );
    }

    @Test
    public void testRemainingAndNull() {
        assertThat( OrderMapper.INSTANCE.withAnyUnmappedAndNull( null ) ).isEqualTo( "DEFAULT" );
        assertThat( OrderMapper.INSTANCE.withAnyUnmappedAndNull( OrderType.STANDARD ) ).isNull();
        assertThat( OrderMapper.INSTANCE.withAnyUnmappedAndNull( OrderType.NORMAL ) ).isEqualTo( "SPECIAL" );
        assertThat( OrderMapper.INSTANCE.withAnyUnmappedAndNull( OrderType.B2B ) ).isEqualTo( "SPECIAL" );
        assertThat( OrderMapper.INSTANCE.withAnyUnmappedAndNull( OrderType.RETAIL ) ).isEqualTo( "SPECIAL" );
        assertThat( OrderMapper.INSTANCE.withAnyUnmappedAndNull( OrderType.EXTRA ) ).isEqualTo( "SPECIAL" );
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
