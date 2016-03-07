/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.value;

import static org.fest.assertions.Assertions.assertThat;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for the generation and invocation of enum mapping methods.
 *
 * @author Gunnar Morling
 */
@IssueKey("128")
@WithClasses({ OrderEntity.class, OrderType.class, OrderDto.class, ExternalOrderType.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class EnumToEnumMappingTest {

    @Test
    @WithClasses( OrderMapper.class )
    public void shouldGenerateEnumMappingMethod() {
        ExternalOrderType target = OrderMapper.INSTANCE.orderTypeToExternalOrderType( OrderType.B2B );
        assertThat( target ).isEqualTo( ExternalOrderType.B2B );

        target = OrderMapper.INSTANCE.orderTypeToExternalOrderType( OrderType.RETAIL );
        assertThat( target ).isEqualTo( ExternalOrderType.RETAIL );
    }

    @Test
    @WithClasses( OrderMapper.class )
    public void shouldConsiderConstantMappings() {
        ExternalOrderType target = OrderMapper.INSTANCE.orderTypeToExternalOrderType( OrderType.EXTRA );
        assertThat( target ).isEqualTo( ExternalOrderType.SPECIAL );

        target = OrderMapper.INSTANCE.orderTypeToExternalOrderType( OrderType.STANDARD );
        assertThat( target ).isEqualTo( ExternalOrderType.DEFAULT );

        target = OrderMapper.INSTANCE.orderTypeToExternalOrderType( OrderType.NORMAL );
        assertThat( target ).isEqualTo( ExternalOrderType.DEFAULT );
    }

    @Test
    @WithClasses( OrderMapper.class )
    public void shouldInvokeEnumMappingMethodForPropertyMapping() {
        OrderEntity order = new OrderEntity();
        order.setOrderType( OrderType.EXTRA );

        OrderDto orderDto = OrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.SPECIAL );
    }

    @Test
    @WithClasses( OrderMapper.class )
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

    @Test
    @WithClasses( SpecialOrderMapper.class )
    public void shouldApplyNullMapping() {
        OrderEntity order = new OrderEntity();
        order.setOrderType( null );

        OrderDto orderDto = SpecialOrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.DEFAULT );
    }

    @Test
    @WithClasses( SpecialOrderMapper.class )
    public void shouldApplyTargetIsNullMapping() {
        OrderEntity order = new OrderEntity();
        order.setOrderType( OrderType.STANDARD );

        OrderDto orderDto = SpecialOrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isNull();
    }

    @Test
    @WithClasses( SpecialOrderMapper.class )
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

    @Test
    @WithClasses( SpecialOrderMapper.class )
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

    @Test
    @WithClasses( DefaultOrderMapper.class )
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


    @Test
    @WithClasses(ErroneousOrderMapperMappingSameConstantTwice.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrderMapperMappingSameConstantTwice.class,
                kind = Kind.ERROR,
                line = 38,
                messageRegExp = "Source value mapping: \"EXTRA\" cannot be mapped more than once\\.")
        }
    )
    public void shouldRaiseErrorIfSameSourceEnumConstantIsMappedTwice() {
    }

    @Test
    @WithClasses(ErroneousOrderMapperUsingUnknownEnumConstants.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrderMapperUsingUnknownEnumConstants.class,
                kind = Kind.ERROR,
                line = 37,
                messageRegExp = "Constant FOO doesn't exist in enum type org.mapstruct.ap.test.value.OrderType\\."),
            @Diagnostic(type = ErroneousOrderMapperUsingUnknownEnumConstants.class,
                kind = Kind.ERROR,
                line = 38,
                messageRegExp = "Constant BAR doesn't exist in enum type org.mapstruct.ap.test.value." +
                    "ExternalOrderType\\.")
        }
    )
    public void shouldRaiseErrorIfUnknownEnumConstantsAreSpecifiedInMapping() {
    }

    @Test
    @WithClasses(ErroneousOrderMapperNotMappingConstantWithoutMatchInTargetType.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrderMapperNotMappingConstantWithoutMatchInTargetType.class,
                kind = Kind.ERROR,
                line = 34,
                messageRegExp = "The following constants from the source enum have no corresponding constant in the " +
                    "target enum and must be be mapped via adding additional mappings: EXTRA, STANDARD, NORMAL")
        }
    )
    public void shouldRaiseErrorIfSourceConstantWithoutMatchingConstantInTargetTypeIsNotMapped() {
    }

    @Test
    @WithClasses(ErroneousOrderMapperDuplicateANY.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrderMapperDuplicateANY.class,
                kind = Kind.ERROR,
                line = 39,
                messageRegExp = "Source = \"<ANY>\" or \"<ANY_UNMAPPED>\" can only be used once\\." )
        }
    )
    public void shouldRaiseErrorIfMappingsContainDuplicateANY() {
    }



}
