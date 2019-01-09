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
public class TargetThisMappingTest {

    @Test
    @WithClasses( {
            CustomerEntityMapper1.class,
            OrderDTO.class,
            CustomerDTO.class,
            CustomerEntity.class,
            Entity.class,
            EntityDTO.class,
            OrderLine.class,
            OrderLineDTO.class,
            OrderEntity.class,
            OrderDTO.class
    } )
    public void testTargetingThis() {
        CustomerDTO ce = new CustomerDTO();
        ce.setName( "customer entity name" );

        EntityDTO e = new EntityDTO();
        e.setId( "entity id" );
        e.setStatus( 1 );
        ce.setEntity( e );

        CustomerEntity c = CustomerEntityMapper1.INSTANCE.map( ce );

        assertThat( c ).isNotNull();
        assertThat( c.getName() ).isEqualTo( ce.getName() );
        assertThat( c.getId() ).isEqualTo( ce.getEntity().getId() );
        assertThat( c.getStatus() ).isEqualTo( ce.getEntity().getStatus() );
    }

    @Test
    @WithClasses( {
            CustomerEntityMapper1.class,
            OrderDTO.class,
            CustomerDTO.class,
            CustomerEntity.class,
            Entity.class,
            EntityDTO.class,
            OrderLine.class,
            OrderLineDTO.class,
            OrderEntity.class,
            OrderDTO.class
    } )
    public void testTargetingThisWithNestedLevels() {
        CustomerDTO ce = new CustomerDTO();
        ce.setName( "customer entity name" );

        EntityDTO e = new EntityDTO();
        e.setId( "entity id" );
        e.setStatus( 1 );
        ce.setEntity( e );

        OrderDTO order = new OrderDTO();
        order.setCustomer( ce );

        CustomerEntity c = CustomerEntityMapper1.INSTANCE.map( order );

        assertThat( c ).isNotNull();
        assertThat( c.getName() ).isEqualTo( ce.getName() );
        assertThat( c.getId() ).isEqualTo( ce.getEntity().getId() );
        assertThat( c.getStatus() ).isEqualTo( ce.getEntity().getStatus() );
    }

    @Test
    @WithClasses( {
            CustomerEntityMapper1.class,
            OrderDTO.class,
            CustomerDTO.class,
            CustomerEntity.class,
            Entity.class,
            EntityDTO.class,
            OrderLine.class,
            OrderLineDTO.class,
            OrderEntity.class,
            OrderDTO.class
    } )
    public void testFromThis() {
        CustomerEntity ce = new CustomerEntity();
        ce.setName( "customer entity name" );
        ce.setId( "entity id" );
        ce.setStatus( 1 );

        CustomerDTO c = CustomerEntityMapper1.INSTANCE.map( ce );

        assertThat( c ).isNotNull();
        assertThat( ce.getName() ).isEqualTo( c.getName() );
        assertThat( ce.getId() ).isEqualTo( c.getEntity().getId() );
        assertThat( ce.getStatus() ).isEqualTo( c.getEntity().getStatus() );
    }

    @Test
    @WithClasses( {
            CustomerEntityMapper1.class,
            OrderDTO.class,
            CustomerDTO.class,
            CustomerEntity.class,
            Entity.class,
            EntityDTO.class,
            OrderLine.class,
            OrderLineDTO.class,
            OrderEntity.class,
            OrderDTO.class
    } )
    public void testWithCollection() {
        CustomerEntity ce = new CustomerEntity();
        ce.setName( "customer entity name" );
        ce.setId( "customer entity id" );
        ce.setStatus( 1 );

        OrderEntity order = new OrderEntity();
        order.setCustomer( ce );
        order.setId( "order id" );

        OrderLine ol = new OrderLine();
        ol.setLine( 1 );

        order.addOrderLine( ol );

        OrderDTO o = CustomerEntityMapper1.INSTANCE.map( order );

        assertThat( o ).isNotNull();
        assertThat( o.getEntity().getId() ).isEqualTo( "order id" );
        assertThat( o.getCustomer().getName() ).isEqualTo( ce.getName() );
        assertThat( o.getCustomer().getEntity().getId() ).isEqualTo( ce.getId() );
    }
}
