/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.enum2enum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Jude Niroshan
 */
@IssueKey("2339")
@WithClasses({
    OrderEntity.class,
    OrderType.class, ExternalOrderType.class
})
public class EnumToEnumThrowExceptionMappingTest {

    @IssueKey("2339")
    @ProcessorTest
    @WithClasses(DefaultOrderThrowExceptionMapper.class)
    public void shouldBeAbleToMapAnyUnmappedToThrowException() {

        assertThatThrownBy( () ->
            DefaultOrderThrowExceptionMapper.INSTANCE
                .orderTypeToExternalOrderTypeAnyUnmappedToException( OrderType.EXTRA ) )
            .isInstanceOf( IllegalArgumentException.class )
            .hasMessage( "Unexpected enum constant: EXTRA" );
    }

    @IssueKey("2339")
    @ProcessorTest
    @WithClasses({ DefaultOrderThrowExceptionMapper.class, ErroneousOrderMapperThrowExceptionAsSourceType.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrderMapperThrowExceptionAsSourceType.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 29,
                message = "Source = \"<THROW_EXCEPTION>\" is not allowed. " +
                    "Target = \"<THROW_EXCEPTION>\" can only be used.")
        }
    )
    public void shouldRaiseErrorWhenThrowExceptionUsedAsSourceType() {
    }

    @IssueKey("2339")
    @ProcessorTest
    @WithClasses({OrderThrowExceptionMapper.class, OrderDto.class})
    public void shouldIgnoreThrowExceptionWhenInverseValueMappings() {

        OrderType target = OrderThrowExceptionMapper.INSTANCE.externalOrderTypeToOrderType( ExternalOrderType.B2B );
        assertThat( target ).isEqualTo( OrderType.B2B );
    }

    @IssueKey("2339")
    @ProcessorTest
    @WithClasses({SpecialThrowExceptionMapper.class, OrderDto.class})
    public void shouldBeAbleToMapAnyRemainingToThrowException() {

        assertThatThrownBy( () ->
            SpecialThrowExceptionMapper.INSTANCE
                .orderTypeToExternalOrderType( OrderType.EXTRA ) )
            .isInstanceOf( IllegalArgumentException.class )
            .hasMessage( "Unexpected enum constant: EXTRA" );
    }

    @IssueKey("2339")
    @ProcessorTest
    @WithClasses({SpecialThrowExceptionMapper.class, OrderDto.class})
    public void shouldBeAbleToMapNullToThrowException() {

        assertThatThrownBy( () ->
            SpecialThrowExceptionMapper.INSTANCE
                .anyRemainingToNullToException( null ) )
            .isInstanceOf( IllegalArgumentException.class )
            .hasMessage( "Unexpected enum constant: null" );
    }
}
