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
public class TargetThisMappingTest2 {

    @Test
    @WithClasses( {
        CustomerEntityMapper2.class,
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
    public void testWithoutSourceWithMapping() {
        EntityDTO e = new EntityDTO();
        e.setId( "entity id" );
        e.setStatus( 1 );

        Entity ee = CustomerEntityMapper2.INSTANCE.map( e );

        assertThat( ee ).isNotNull();
        assertThat( ee.getId() ).isEqualTo( e.getId() );
    }

    @Test
    @WithClasses( {
        CustomerEntityMapper2.class,
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
    public void testWithoutSourceWithMultipapping() {
        CustomerDTO ce = new CustomerDTO();
        ce.setName( "customer entity name" );

        EntityDTO e = new EntityDTO();
        e.setId( "entity id" );
        e.setStatus( 1 );
        ce.setEntity( e );

        CustomerEntity c = CustomerEntityMapper2.INSTANCE.map( ce );

        assertThat( c ).isNotNull();
        assertThat( c.getName() ).isEqualTo( ce.getName() );
    }

    @Test
    @WithClasses( {
        CustomerEntityMapper2.class,
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
    public void testMapNameOnly() {
        CustomerDTO ce = new CustomerDTO();
        ce.setName( "customer entity name" );

        EntityDTO e = new EntityDTO();
        e.setId( "entity id" );
        e.setStatus( 1 );
        ce.setEntity( e );

        CustomerEntity c = CustomerEntityMapper2.INSTANCE.mapNameOnly( ce );

        assertThat( c ).isNotNull();
        assertThat( c.getName() ).isEqualTo( ce.getName() );
    }
}
