/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dainius Figoras
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses( {
    OrderDTO.class,
    CustomerDTO.class,
    CustomerItem.class,
    CustomerItem.class,
    ItemDTO.class,
    OrderItem.class,
    OrderDTO.class
} )
public class TargetThisMappingTest {

    @Test
    @WithClasses( SimpleMapper.class )
    public void testTargetingThis() {
        CustomerDTO ce = new CustomerDTO();
        ce.setName( "customer name" );

        ItemDTO e = new ItemDTO();
        e.setId( "item id" );
        e.setStatus( 1 );
        ce.setItem( e );

        CustomerItem c = SimpleMapper.INSTANCE.map( ce );

        assertThat( c ).isNotNull();
        assertThat( c.getName() ).isNull();
        assertThat( c.getId() ).isEqualTo( ce.getItem().getId() );
        assertThat( c.getStatus() ).isEqualTo( ce.getItem().getStatus() );
    }

    @Test
    @WithClasses( NestedMapper.class )
    public void testTargetingThisWithNestedLevels() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName( "customer name" );

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId( "item id" );
        itemDTO.setStatus( 1 );
        customerDTO.setItem( itemDTO );

        OrderDTO order = new OrderDTO();
        order.setCustomer( customerDTO );

        OrderItem c = NestedMapper.INSTANCE.map( order );

        assertThat( c ).isNotNull();
        assertThat( c.getId() ).isEqualTo( customerDTO.getItem().getId() );
        assertThat( c.getStatus() ).isEqualTo( customerDTO.getItem().getStatus() );
    }

    @Test
    @WithClasses( SimpleMapperWithIgnore.class )
    public void testTargetingThisWithIgnore() {
        CustomerDTO ce = new CustomerDTO();
        ce.setName( "customer name" );

        ItemDTO e = new ItemDTO();
        e.setId( "item id" );
        e.setStatus( 1 );
        ce.setItem( e );

        CustomerItem c = SimpleMapperWithIgnore.INSTANCE.map( ce );

        assertThat( c ).isNotNull();
        assertThat( c.getName() ).isEqualTo( "customer name" );
        assertThat( c.getId() ).isNull();
        assertThat( c.getStatus() ).isEqualTo( ce.getItem().getStatus() );
    }

    @Test
    @WithClasses( ErroneousNestedMapper.class )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousNestedMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                messageRegExp = "^Several possible source properties for target property \"id\"\\.$"),
            @Diagnostic(type = ErroneousNestedMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                messageRegExp = "^Several possible source properties for target property \"status\"\\.$")
        }
    )
    public void testNestedDuplicates() {
    }

    @Test
    @WithClasses( ConfictsResolvedNestedMapper.class )
    public void testWithConflictsResolved() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setItem( new ItemDTO() );
        orderDTO.getItem().setId( "item1" );
        orderDTO.getItem().setStatus( 1 );
        orderDTO.setCustomer( new CustomerDTO() );
        orderDTO.getCustomer().setName( "customer name" );
        orderDTO.getCustomer().setItem( new ItemDTO() );
        orderDTO.getCustomer().getItem().setId( "item2" );
        orderDTO.getCustomer().getItem().setStatus( 2 );

        OrderItem c = ConfictsResolvedNestedMapper.INSTANCE.map( orderDTO );

        assertThat( c ).isNotNull();
        assertThat( c.getStatus() ).isEqualTo( orderDTO.getItem().getStatus() );
        assertThat( c.getId() ).isEqualTo( orderDTO.getCustomer().getItem().getId() );
    }

    @Test
    @WithClasses( FlatteningMapper.class )
    public void testFlattening() {

        FlatteningMapper.CustomerDTO customerDTO = new FlatteningMapper.CustomerDTO();
        customerDTO.setName( new FlatteningMapper.NameDTO() );
        customerDTO.getName().setName( "john doe" );
        customerDTO.getName().setId( "1" );
        customerDTO.setAccount( new FlatteningMapper.AccountDTO() );
        customerDTO.getAccount().setDetails( "nice guys" );
        customerDTO.getAccount().setNumber( "11223344" );

        FlatteningMapper.Customer customer = FlatteningMapper.INSTANCE.flatten( customerDTO );

        assertThat( customer ).isNotNull();
        assertThat( customer.getName() ).isEqualTo( "john doe" );
        assertThat( customer.getId() ).isEqualTo( "1" );
        assertThat( customer.getDetails() ).isEqualTo( "nice guys" );
        assertThat( customer.getNumber() ).isEqualTo( "11223344" );

        FlatteningMapper.CustomerDTO customerDTO2 = FlatteningMapper.INSTANCE.expand( customer );

        assertThat( customerDTO2 ).isNotNull();
        assertThat( customerDTO2.getName().getName() ).isEqualTo( "john doe" );
        assertThat( customerDTO2.getName().getId() ).isEqualTo( "1" );
        assertThat( customerDTO2.getAccount().getDetails() ).isEqualTo( "nice guys" );
        assertThat( customerDTO2.getAccount().getNumber() ).isEqualTo( "11223344" );
    }
}
