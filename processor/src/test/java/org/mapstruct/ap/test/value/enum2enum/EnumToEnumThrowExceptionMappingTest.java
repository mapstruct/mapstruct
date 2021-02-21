package org.mapstruct.ap.test.value.enum2enum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@IssueKey("2339")
@WithClasses({
    DefaultOrderThrowExceptionMapper.class, OrderEntity.class,
    OrderType.class, ExternalOrderType.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class EnumToEnumThrowExceptionMappingTest {

    @IssueKey("2339")
    @Test
    public void shouldThrowExceptionWhenRequestingEnumsWithExpectedExceptions() {

        assertThatThrownBy( () ->
            DefaultOrderThrowExceptionMapper.INSTANCE.orderTypeToExternalOrderTypeWithException( OrderType.EXTRA ) )
            .isInstanceOf( IllegalArgumentException.class )
            .hasMessage( "Unexpected enum constant: EXTRA" );
    }

    @IssueKey("2339")
    @Test
    @WithClasses(ErroneousOrderMapperThrowExceptionAsSourceType.class)
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
}
