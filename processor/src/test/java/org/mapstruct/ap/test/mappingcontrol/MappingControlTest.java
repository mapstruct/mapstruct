/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * @author Sjaak Derksen
 */
@IssueKey("695")
@WithClasses({
    CoolBeerDTO.class,
    ShelveDTO.class,
    Fridge.class,
    FridgeDTO.class,
    CustomerDto.class,
    OrderItemDto.class,
    OrderItemKeyDto.class,
    UseDirect.class,
    UseComplex.class
})
public class MappingControlTest {

    /**
     * Baseline Test, normal, direct allowed
     */
    @ProcessorTest
    @WithClasses(DirectMapper.class)
    public void directSelectionAllowed() {

        FridgeDTO in = createFridgeDTO();
        FridgeDTO out = DirectMapper.INSTANCE.map( in );

        assertThat( out ).isNotNull();
        assertThat( out.getShelve() ).isNotNull();
        assertThat( out.getShelve() ).isSameAs( in.getShelve() );
    }

    /**
     * Test the deep cloning annotation
     */
    @ProcessorTest
    @WithClasses(CloningMapper.class)
    public void testDeepCloning() {

        FridgeDTO in = createFridgeDTO();
        FridgeDTO out = CloningMapper.INSTANCE.clone( in );

        assertThat( out ).isNotNull();
        assertThat( out.getShelve() ).isNotNull();
        assertThat( out.getShelve() ).isNotSameAs( in.getShelve() );
        assertThat( out.getShelve().getCoolBeer() ).isNotSameAs( in.getShelve().getCoolBeer() );
        assertThat( out.getShelve().getCoolBeer().getBeerCount() ).isEqualTo( "5" );
    }

    @ProcessorTest
    @IssueKey("3135")
    @WithClasses(CloningBeanMappingMapper.class)
    public void testDeepCloningViaBeanMapping() {

        FridgeDTO in = createFridgeDTO();
        FridgeDTO out = CloningBeanMappingMapper.INSTANCE.clone( in );

        assertThat( out ).isNotNull();
        assertThat( out.getShelve() ).isNotNull();
        assertThat( out.getShelve() ).isNotSameAs( in.getShelve() );
        assertThat( out.getShelve().getCoolBeer() ).isNotSameAs( in.getShelve().getCoolBeer() );
        assertThat( out.getShelve().getCoolBeer().getBeerCount() ).isEqualTo( "5" );
    }

    /**
     * Test the deep cloning annotation with lists
     */
    @ProcessorTest
    @WithClasses(CloningListMapper.class)
    public void testDeepCloningListsAndMaps() {

        CustomerDto in = new CustomerDto();
        in.setId( 10L );
        in.setCustomerName( "Jaques" );
        OrderItemDto order1 = new OrderItemDto();
        order1.setName( "Table" );
        order1.setQuantity( 2L );
        in.setOrders( new ArrayList<>( Collections.singleton( order1 ) ) );
        OrderItemKeyDto key = new OrderItemKeyDto();
        key.setStockNumber( 5 );
        Map<OrderItemKeyDto, OrderItemDto> stock = new HashMap<>();
        stock.put( key, order1 );
        in.setStock( stock );

        CustomerDto out = CloningListMapper.INSTANCE.clone( in );

        assertThat( out.getId() ).isEqualTo( 10 );
        assertThat( out.getCustomerName() ).isEqualTo( "Jaques" );
        assertThat( out.getOrders() )
            .extracting( "name", "quantity" )
            .containsExactly( tuple( "Table", 2L ) );
        assertThat( out.getStock() ).isNotNull();
        assertThat( out.getStock() ).hasSize( 1 );

        Map.Entry<OrderItemKeyDto, OrderItemDto> entry = out.getStock().entrySet().iterator().next();
        assertThat( entry.getKey().getStockNumber() ).isEqualTo( 5 );
        assertThat( entry.getValue().getName() ).isEqualTo( "Table" );
        assertThat( entry.getValue().getQuantity() ).isEqualTo( 2L );

        // check mapper really created new objects
        assertThat( out ).isNotSameAs( in );
        assertThat( out.getOrders().get( 0 ) ).isNotSameAs( order1 );
        assertThat( entry.getKey() ).isNotSameAs( key );
        assertThat( entry.getValue() ).isNotSameAs( order1 );
        assertThat( entry.getValue() ).isNotSameAs( out.getOrders().get( 0 ) );
    }

    /**
     * This is a nice test. MapStruct looks for a way to map ShelveDto to ShelveDto.
     * <p>
     * MapStruct gets too creative when we allow complex (2 step mappings) to convert if we also allow
     * it to forge methods (which is contradiction with the fact that we do not allow methods on this mapper)
     */
    @ProcessorTest
    @WithClasses(ErroneousDirectMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousDirectMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 17,
                message = "Can't map property \"ShelveDTO shelve\" to \"ShelveDTO shelve\". " +
                    "Consider to declare/implement a mapping method: \"ShelveDTO map(ShelveDTO value)\"."
            )
        })
    public void directSelectionNotAllowed() {
    }

    /**
     * Baseline Test, normal, method allowed
     */
    @ProcessorTest
    @WithClasses(MethodMapper.class)
    public void methodSelectionAllowed() {
        Fridge fridge = MethodMapper.INSTANCE.map( createFridgeDTO() );

        assertThat( fridge ).isNotNull();
        assertThat( fridge.getBeerCount() ).isEqualTo( 5 );
        assertThat( fridge.getBeerCount() ).isEqualTo( 5 );
    }

    @ProcessorTest
    @WithClasses(ErroneousMethodMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMethodMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 17,
                message = "Can't map property \"ShelveDTO shelve\" to \"int beerCount\". " +
                    "Consider to declare/implement a mapping method: \"int map(ShelveDTO value)\"."
            )
        })
    public void methodSelectionNotAllowed() {
    }

    /**
     * Baseline Test, normal, conversion allowed
     */
    @ProcessorTest
    @WithClasses(ConversionMapper.class)
    public void conversionSelectionAllowed() {
        Fridge fridge = ConversionMapper.INSTANCE.map( createFridgeDTO().getShelve().getCoolBeer() );

        assertThat( fridge ).isNotNull();
        assertThat( fridge.getBeerCount() ).isEqualTo( 5 );
    }

    @ProcessorTest
    @WithClasses(ErroneousConversionMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousConversionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 16,
                message = "Can't map property \"String beerCount\" to \"int beerCount\". " +
                    "Consider to declare/implement a mapping method: \"int map(String value)\"."
            )
        })
    public void conversionSelectionNotAllowed() {
    }

    /**
     * Baseline Test, normal, complex mapping allowed
     */
    @ProcessorTest
    @WithClasses(ComplexMapper.class)
    public void complexSelectionAllowed() {
        Fridge fridge = ComplexMapper.INSTANCE.map( createFridgeDTO() );

        assertThat( fridge ).isNotNull();
        assertThat( fridge.getBeerCount() ).isEqualTo( 5 );
    }

    @ProcessorTest
    @WithClasses(ErroneousComplexMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousComplexMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 17,
                message = "Can't map property \"ShelveDTO shelve\" to \"int beerCount\". " +
                    "Consider to declare/implement a mapping method: \"int map(ShelveDTO value)\"."
            )
        })
    public void complexSelectionNotAllowed() {
    }

    @ProcessorTest
    @WithClasses({ Config.class, ErroneousComplexMapperWithConfig.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousComplexMapperWithConfig.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 17,
                message = "Can't map property \"ShelveDTO shelve\" to \"int beerCount\". " +
                    "Consider to declare/implement a mapping method: \"int map(ShelveDTO value)\"."
            )
        })
    public void complexSelectionNotAllowedWithConfig() {
    }

    @ProcessorTest
    @IssueKey("3186")
    @WithClasses({
        ErroneousMethodAndBuiltInMapper.class,
        NoConversion.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMethodAndBuiltInMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                message = "Can't map property \"ZonedDateTime time\" to \"Date time\". " +
                    "Consider to declare/implement a mapping method: \"Date map(ZonedDateTime value)\"."
            )
        })
    public void conversionSelectionNotAllowedInTwoStepMethodBuiltIdConversion() {
    }

    @ProcessorTest
    @IssueKey("3186")
    @WithClasses({
        ErroneousBuiltInAndBuiltInMapper.class,
        NoConversion.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousBuiltInAndBuiltInMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 19,
                message = "Can't map property \"ZonedDateTime time\" to \"Date time\". " +
                    "Consider to declare/implement a mapping method: \"Date map(ZonedDateTime value)\"."
            )
        })
    public void conversionSelectionNotAllowedInTwoStepBuiltInBuiltInConversion() {
    }

    @ProcessorTest
    @IssueKey("3186")
    @WithClasses({
        ErroneousBuiltInAndMethodMapper.class,
        NoConversion.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousBuiltInAndMethodMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 21,
                message = "Can't map property \"int time\" to \"Calendar time\". " +
                    "Consider to declare/implement a mapping method: \"Calendar map(int value)\"."
            )
        })
    public void conversionSelectionNotAllowedInTwoStepBuiltInMethodConversion() {
    }

    @ProcessorTest
    @IssueKey("3186")
    @WithClasses({
        ErroneousMethodAndConversionMapper.class,
        NoConversion.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMethodAndConversionMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 19,
                message = "Can't map property \"Date time\" to \"long time\". " +
                    "Consider to declare/implement a mapping method: \"long map(Date value)\"."
            )
        })
    public void conversionSelectionNotAllowedInTwoStepMethodConversionConversion() {
    }

    @ProcessorTest
    @IssueKey("3186")
    @WithClasses({
        ErroneousConversionAndMethodMapper.class,
        NoConversion.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousConversionAndMethodMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 19,
                message = "Can't map property \"int time\" to \"Date time\". " +
                    "Consider to declare/implement a mapping method: \"Date map(int value)\"."
            )
        })
    public void conversionSelectionNotAllowedInTwoStepConversionMethodConversion() {
    }

    private FridgeDTO createFridgeDTO() {
        FridgeDTO fridgeDTO = new FridgeDTO();
        ShelveDTO shelveDTO = new ShelveDTO();
        CoolBeerDTO coolBeerDTO = new CoolBeerDTO();
        fridgeDTO.setShelve( shelveDTO );
        shelveDTO.setCoolBeer( coolBeerDTO );
        coolBeerDTO.setBeerCount( "5" );
        return fridgeDTO;
    }
}
