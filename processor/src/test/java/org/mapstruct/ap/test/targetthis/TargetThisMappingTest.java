/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

import javax.tools.Diagnostic.Kind;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses( {
    CustomerMapper1.class,
    CustomerMapper2.class,
    OrderDTO.class,
    CustomerDTO.class,
    CustomerItem.class,
    Item.class,
    ItemDTO.class,
    OrderLine.class,
    OrderLineDTO.class,
    OrderItem.class,
    OrderDTO.class,
    DogDTO.class,
    Dog.class,
    AnimalDTO.class,
    Animal.class,
    MapConfig.class,
    SaleOrder.class,
    SaleOrderDTO.class
} )
public class TargetThisMappingTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        CustomerMapper1.class,
        CustomerMapper2.class
    );

    @Test
    @WithClasses( {
        CustomerMapper3.class
    } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = CustomerMapper3.class,
                kind = Kind.WARNING,
                line = 26,
                messageRegExp = "Unmapped source properties: \"type, color, age, number\""),
            @Diagnostic(type = CustomerMapper3.class,
                kind = Kind.WARNING,
                line = 26,
                messageRegExp = "Unmapped source properties: \"weight, color\"")
        }
    )
    public void testDog() {
        DogDTO dto = new DogDTO();
        dto.setType( "dog type" );

        AnimalDTO e = new AnimalDTO();
        e.setWeight( 123 );

        dto.setAnimal( e );

        Dog domain = CustomerMapper3.INSTANCE.map( dto );

        assertThat( domain ).isNotNull();
        assertThat( domain.getType() ).isEqualTo( dto.getType() );
        assertThat( domain.getWeight() ).isEqualTo( e.getWeight() );

        DogDTO back = CustomerMapper3.INSTANCE.map( domain );

        assertThat( back ).isNotNull();
        assertThat( back.getType() ).isEqualTo( domain.getType() );
        assertThat( back.getAnimal().getWeight() ).isEqualTo( domain.getWeight() );
    }

    @Test
    public void testTargetingThis() {
        CustomerDTO ce = new CustomerDTO();
        ce.setName( "customer name" );

        ItemDTO e = new ItemDTO();
        e.setId( "item id" );
        e.setStatus( 1 );
        ce.setItem( e );

        CustomerItem c = CustomerMapper1.INSTANCE.map( ce );

        assertThat( c ).isNotNull();
        assertThat( c.getName() ).isEqualTo( ce.getName() );
        assertThat( c.getId() ).isEqualTo( ce.getItem().getId() );
        assertThat( c.getStatus() ).isEqualTo( ce.getItem().getStatus() );
    }

    @Test
    public void testTargetingThisWithNestedLevels() {
        CustomerDTO ce = new CustomerDTO();
        ce.setName( "customer name" );

        ItemDTO e = new ItemDTO();
        e.setId( "item id" );
        e.setStatus( 1 );
        ce.setItem( e );

        OrderDTO order = new OrderDTO();
        order.setCustomer( ce );

        OrderItem c = CustomerMapper1.INSTANCE.map( order );

        assertThat( c ).isNotNull();
        assertThat( c.getCustomer() ).isNotNull();
        assertThat( c.getCustomer().getName() ).isEqualTo( ce.getName() );
        assertThat( c.getId() ).isEqualTo( ce.getItem().getId() );
        assertThat( c.getStatus() ).isEqualTo( ce.getItem().getStatus() );
    }

    @Test
    public void testMapDoubleTarget() {
        CustomerDTO ce = new CustomerDTO();
        ce.setName( "customer name" );

        ItemDTO e = new ItemDTO();
        e.setId( "item id" );
        e.setStatus( 1 );
        ce.setItem( e );

        OrderDTO order = new OrderDTO();
        order.setCustomer( ce );
        order.setItem( e );

        SaleOrderDTO saleOrder = new SaleOrderDTO();
        saleOrder.setItem( e );
        saleOrder.setOrder( order );
        saleOrder.setNumber( "double mapping test" );

        SaleOrder c = CustomerMapper1.INSTANCE.mapDoubleTarget( saleOrder );

        assertThat( c ).isNotNull();
        assertThat( c.getNumber() ).isEqualTo( saleOrder.getNumber() );
        assertThat( c.getId() ).isEqualTo( e.getId() );
        assertThat( c.getStatus() ).isEqualTo( e.getStatus() );
    }

    @Test
    public void testFromThis() {
        CustomerItem ce = new CustomerItem();
        ce.setName( "customer name" );
        ce.setId( "item id" );
        ce.setStatus( 1 );

        CustomerDTO c = CustomerMapper1.INSTANCE.inverseMap( ce );

        assertThat( c ).isNotNull();
        assertThat( ce.getName() ).isEqualTo( c.getName() );
        assertThat( ce.getId() ).isEqualTo( c.getItem().getId() );
        assertThat( ce.getStatus() ).isEqualTo( c.getItem().getStatus() );
    }

    @Test
    public void testWithCollection() {
        CustomerItem ce = new CustomerItem();
        ce.setName( "customer name" );
        ce.setId( "customer id" );
        ce.setStatus( 1 );

        OrderItem order = new OrderItem();
        order.setCustomer( ce );
        order.setId( "order id" );

        OrderLine ol = new OrderLine();
        ol.setLine( 1 );

//        order.addOrderLine( ol );

        OrderDTO o = CustomerMapper1.INSTANCE.map( order );

        assertThat( o ).isNotNull();
        assertThat( o.getItem().getId() ).isEqualTo( "order id" );
        assertThat( o.getCustomer().getName() ).isEqualTo( ce.getName() );
        assertThat( o.getCustomer().getItem().getId() ).isEqualTo( ce.getId() );
    }

    @Test
    public void testUpdateDto() {
        CustomerItem ce = new CustomerItem();
        ce.setName( "customer name" );
        ce.setId( "item id" );
        ce.setStatus( 1 );

        CustomerDTO c = new CustomerDTO();

        CustomerMapper1.INSTANCE.update( ce, c );

        assertThat( c ).isNotNull();
        assertThat( ce.getName() ).isEqualTo( c.getName() );
        assertThat( ce.getId() ).isEqualTo( c.getItem().getId() );
        assertThat( ce.getStatus() ).isEqualTo( c.getItem().getStatus() );
    }

    @Test
    public void testUpdateItem() {
        CustomerDTO dto = new CustomerDTO();
        dto.setName( "customer name" );

        ItemDTO e = new ItemDTO();
        e.setId( "item id" );
        e.setStatus( 1 );
        dto.setItem( e );

        CustomerItem c = new CustomerItem();

        CustomerMapper1.INSTANCE.update( dto, c );

        assertThat( c ).isNotNull();
        assertThat( c.getName() ).isEqualTo( dto.getName() );
        assertThat( c.getId() ).isEqualTo( dto.getItem().getId() );
        assertThat( c.getStatus() ).isEqualTo( dto.getItem().getStatus() );
    }

    @Test
    public void testWithoutSourceWithMappingWithNoSource() {
        ItemDTO e = new ItemDTO();
        e.setId( "item id" );
        e.setStatus( 1 );

        Item ee = CustomerMapper2.INSTANCE.map( e );

        assertThat( ee ).isNotNull();
        assertThat( ee.getId() ).isEqualTo( e.getId() );
    }

    @Test
    public void testWithoutSourceWithMultipappingWithNoSource() {
        CustomerDTO ce = new CustomerDTO();
        ce.setName( "customer name" );

        ItemDTO e = new ItemDTO();
        e.setId( "item id" );
        e.setStatus( 1 );
        ce.setItem( e );

        CustomerItem c = CustomerMapper2.INSTANCE.map( ce );

        assertThat( c ).isNotNull();
        assertThat( c.getName() ).isEqualTo( ce.getName() );
    }

    @Test
    public void testMapNameOnlyWithNoSource() {
        CustomerDTO ce = new CustomerDTO();
        ce.setName( "customer item name" );

        ItemDTO e = new ItemDTO();
        e.setId( "item id" );
        e.setStatus( 1 );
        ce.setItem( e );

        CustomerItem c = CustomerMapper2.INSTANCE.mapNameOnly( ce );

        assertThat( c ).isNotNull();
        assertThat( c.getName() ).isEqualTo( ce.getName() );
    }
}
