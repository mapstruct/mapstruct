/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.enums;

import static org.assertj.core.api.Assertions.assertThat;

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
public class EnumMappingTest {

    @Test
    @WithClasses( OrderMapper.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = OrderMapper.class,
                kind = Kind.WARNING,
                line = 41,
                messageRegExp = "Mapping of Enums via @Mapping is going to be removed in future versions of "
                    + "MapStruct\\. Please use @ValueMapping instead!")
        }
    )
    public void shouldGenerateEnumMappingMethod() {
        ExternalOrderType target = OrderMapper.INSTANCE.orderTypeToExternalOrderType( OrderType.B2B );
        assertThat( target ).isEqualTo( ExternalOrderType.B2B );

        target = OrderMapper.INSTANCE.orderTypeToExternalOrderType( OrderType.RETAIL );
        assertThat( target ).isEqualTo( ExternalOrderType.RETAIL );
    }

    @Test
    @WithClasses(OrderMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = OrderMapper.class,
                kind = Kind.WARNING,
                line = 41,
                messageRegExp = "Mapping of Enums via @Mapping is going to be removed in future versions of "
                    + "MapStruct\\. Please use @ValueMapping instead!")
        }
    )
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
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = OrderMapper.class,
                kind = Kind.WARNING,
                line = 41,
                messageRegExp = "Mapping of Enums via @Mapping is going to be removed in future versions of "
                    + "MapStruct\\. Please use @ValueMapping instead!")
        }
    )
    public void shouldInvokeEnumMappingMethodForPropertyMapping() {
        OrderEntity order = new OrderEntity();
        order.setOrderType( OrderType.EXTRA );

        OrderDto orderDto = OrderMapper.INSTANCE.orderEntityToDto( order );
        assertThat( orderDto ).isNotNull();
        assertThat( orderDto.getOrderType() ).isEqualTo( ExternalOrderType.SPECIAL );
    }

    @Test
    @WithClasses( ErroneousOrderMapperMappingSameConstantTwice.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {

            @Diagnostic(type = ErroneousOrderMapperMappingSameConstantTwice.class,
                kind = Kind.ERROR,
                line = 42,
                messageRegExp = "One enum constant must not be mapped to more than one target constant, but " +
                    "constant EXTRA is mapped to SPECIAL, DEFAULT\\."),
            @Diagnostic(type = ErroneousOrderMapperMappingSameConstantTwice.class,
                kind = Kind.WARNING,
                line = 42,
                messageRegExp = "Mapping of Enums via @Mapping is going to be removed in future versions of "
                    + "MapStruct\\. Please use @ValueMapping instead!")
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
                kind = Kind.WARNING,
                line = 40,
                messageRegExp = "Mapping of Enums via @Mapping is going to be removed in future versions of "
                    + "MapStruct\\. Please use @ValueMapping instead!"),
            @Diagnostic(type = ErroneousOrderMapperUsingUnknownEnumConstants.class,
                kind = Kind.ERROR,
                line = 37,
                messageRegExp = "Constant FOO doesn't exist in enum type org.mapstruct.ap.test.enums.OrderType\\."),
            @Diagnostic(type = ErroneousOrderMapperUsingUnknownEnumConstants.class,
                kind = Kind.ERROR,
                line = 38,
                messageRegExp = "Constant BAR doesn't exist in enum type org.mapstruct.ap.test.enums." +
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
}
