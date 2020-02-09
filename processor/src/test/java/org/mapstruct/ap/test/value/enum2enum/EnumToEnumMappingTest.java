/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.enum2enum;

import javax.tools.Diagnostic.Kind;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the generation and invocation of enum mapping methods.
 *
 * @author Gunnar Morling, Sjaak Derksen
 */
@IssueKey("128")
@WithClasses({  OrderMapper.class, SpecialOrderMapper.class, DefaultOrderMapper.class, OrderEntity.class,
    OrderType.class, OrderDto.class, ExternalOrderType.class })
public class EnumToEnumMappingTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        DefaultOrderMapper.class,
        OrderMapper.class,
        SpecialOrderMapper.class
    );

    @ProcessorTest
    public void shouldGenerateEnumMappingMethod() {
        ExternalOrderType target = OrderMapper.INSTANCE.orderTypeToExternalOrderType( OrderType.B2B );
        assertThat( target ).isEqualTo( ExternalOrderType.B2B );

        target = OrderMapper.INSTANCE.orderTypeToExternalOrderType( OrderType.RETAIL );
        assertThat( target ).isEqualTo( ExternalOrderType.RETAIL );
    }

    @ProcessorTest
    public void shouldConsiderConstantMappings() {
        ExternalOrderType target = OrderMapper.INSTANCE.orderTypeToExternalOrderType( OrderType.EXTRA );
        assertThat( target ).isEqualTo( ExternalOrderType.SPECIAL );

        target = OrderMapper.INSTANCE.orderTypeToExternalOrderType( OrderType.STANDARD );
        assertThat( target ).isEqualTo( ExternalOrderType.DEFAULT );

        target = OrderMapper.INSTANCE.orderTypeToExternalOrderType( OrderType.NORMAL );
        assertThat( target ).isEqualTo( ExternalOrderType.DEFAULT );
    }

    @ProcessorTest
    public void shouldInvokeEnumMappingMethodForPropertyMapping() {
        OrderEntity order = new OrderEntity();
        order.setOrderType( OrderType.EXTRA );

        OrderDto orderDto = OrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.SPECIAL );
    }

    @ProcessorTest
    public void shouldApplyReverseMappings() {

        OrderType result =  OrderMapper.INSTANCE.externalOrderTypeToOrderType( ExternalOrderType.SPECIAL );
        assertThat( result ).isEqualTo( OrderType.EXTRA );

        result =  OrderMapper.INSTANCE.externalOrderTypeToOrderType( ExternalOrderType.DEFAULT );
        assertThat( result ).isEqualTo( OrderType.STANDARD );

        result =  OrderMapper.INSTANCE.externalOrderTypeToOrderType( ExternalOrderType.RETAIL );
        assertThat( result ).isEqualTo( OrderType.RETAIL );

        result =  OrderMapper.INSTANCE.externalOrderTypeToOrderType( ExternalOrderType.B2B );
        assertThat( result ).isEqualTo( OrderType.B2B );

    }

    @ProcessorTest
    public void shouldApplyNullMapping() {
        OrderEntity order = new OrderEntity();
        order.setOrderType( null );

        OrderDto orderDto = SpecialOrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.DEFAULT );
    }

    @ProcessorTest
    public void shouldApplyTargetIsNullMapping() {
        OrderEntity order = new OrderEntity();
        order.setOrderType( OrderType.STANDARD );

        OrderDto orderDto = SpecialOrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isNull();
    }

    @ProcessorTest
    public void shouldApplyDefaultMappings() {
        OrderEntity order = new OrderEntity();

        // try all other
        order.setOrderType( OrderType.B2B );

        OrderDto orderDto = SpecialOrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.B2B );

        order.setOrderType( OrderType.EXTRA );

        orderDto = SpecialOrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.SPECIAL );

        order.setOrderType( OrderType.NORMAL );

        orderDto = SpecialOrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.SPECIAL );

        order.setOrderType( OrderType.RETAIL );

        orderDto = SpecialOrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.RETAIL );
    }

    @ProcessorTest
    public void shouldApplyDefaultReverseMappings() {

        OrderType result =  SpecialOrderMapper.INSTANCE.externalOrderTypeToOrderType( ExternalOrderType.SPECIAL );
        assertThat( result ).isEqualTo( OrderType.EXTRA );

        result =  SpecialOrderMapper.INSTANCE.externalOrderTypeToOrderType( ExternalOrderType.DEFAULT );
        assertThat( result ).isNull();

        result =  SpecialOrderMapper.INSTANCE.externalOrderTypeToOrderType( ExternalOrderType.RETAIL );
        assertThat( result ).isEqualTo( OrderType.RETAIL );

        result =  SpecialOrderMapper.INSTANCE.externalOrderTypeToOrderType( ExternalOrderType.B2B );
        assertThat( result ).isEqualTo( OrderType.B2B );

    }

    @ProcessorTest
    public void shouldMappAllUnmappedToDefault() {

        OrderEntity order = new OrderEntity();
        order.setOrderType( OrderType.RETAIL );
        OrderDto orderDto = DefaultOrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.DEFAULT );

        order.setOrderType( OrderType.B2B );
        orderDto = DefaultOrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.DEFAULT );

        order.setOrderType( OrderType.EXTRA );
        orderDto = DefaultOrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.DEFAULT );

        order.setOrderType( OrderType.STANDARD );
        orderDto = DefaultOrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.DEFAULT );

        order.setOrderType( OrderType.NORMAL );
        orderDto = DefaultOrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.DEFAULT );

    }

    @IssueKey( "1091" )
    @ProcessorTest
    public void shouldMapAnyRemainingToNullCorrectly() {
        ExternalOrderType externalOrderType = SpecialOrderMapper.INSTANCE.anyRemainingToNull( OrderType.RETAIL );
        assertThat( externalOrderType )
            .isNotNull()
            .isEqualTo( ExternalOrderType.RETAIL );

        externalOrderType = SpecialOrderMapper.INSTANCE.anyRemainingToNull( OrderType.B2B );
        assertThat( externalOrderType )
            .isNotNull()
            .isEqualTo( ExternalOrderType.B2B );

        externalOrderType = SpecialOrderMapper.INSTANCE.anyRemainingToNull( OrderType.EXTRA );
        assertThat( externalOrderType ).isNull();

        externalOrderType = SpecialOrderMapper.INSTANCE.anyRemainingToNull( OrderType.STANDARD );
        assertThat( externalOrderType ).isNull();

        externalOrderType = SpecialOrderMapper.INSTANCE.anyRemainingToNull( OrderType.NORMAL );
        assertThat( externalOrderType ).isNull();
    }

    @ProcessorTest
    @WithClasses(ErroneousOrderMapperMappingSameConstantTwice.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrderMapperMappingSameConstantTwice.class,
                kind = Kind.ERROR,
                line = 27,
                messageRegExp = "Source value mapping: \"EXTRA\" cannot be mapped more than once\\.")
        }
    )
    public void shouldRaiseErrorIfSameSourceEnumConstantIsMappedTwice() {
    }

    @ProcessorTest
    @WithClasses(ErroneousOrderMapperUsingUnknownEnumConstants.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrderMapperUsingUnknownEnumConstants.class,
                kind = Kind.ERROR,
                line = 26,
                messageRegExp = "Constant FOO doesn't exist in enum type org.mapstruct.ap.test.value.OrderType\\."),
            @Diagnostic(type = ErroneousOrderMapperUsingUnknownEnumConstants.class,
                kind = Kind.ERROR,
                line = 27,
                messageRegExp = "Constant BAR doesn't exist in enum type org.mapstruct.ap.test.value." +
                    "ExternalOrderType\\.")
        }
    )
    public void shouldRaiseErrorIfUnknownEnumConstantsAreSpecifiedInMapping() {
    }

    @ProcessorTest
    @WithClasses(ErroneousOrderMapperNotMappingConstantWithoutMatchInTargetType.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrderMapperNotMappingConstantWithoutMatchInTargetType.class,
                kind = Kind.ERROR,
                line = 23,
                messageRegExp = "The following constants from the source enum have no corresponding constant in the " +
                    "target enum and must be be mapped via adding additional mappings: EXTRA, STANDARD, NORMAL")
        }
    )
    public void shouldRaiseErrorIfSourceConstantWithoutMatchingConstantInTargetTypeIsNotMapped() {
    }

    @ProcessorTest
    @WithClasses(ErroneousOrderMapperDuplicateANY.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrderMapperDuplicateANY.class,
                kind = Kind.ERROR,
                line = 28,
                messageRegExp = "Source = \"<ANY_REMAINING>\" or \"<ANY_UNMAPPED>\" can only be used once\\." )
        }
    )
    public void shouldRaiseErrorIfMappingsContainDuplicateANY() {
    }
}
