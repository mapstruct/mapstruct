/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

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
}
