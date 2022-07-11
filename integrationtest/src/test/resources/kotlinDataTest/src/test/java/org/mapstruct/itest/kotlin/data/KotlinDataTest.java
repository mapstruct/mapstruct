/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.kotlin.data;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mapstruct.itest.kotlin.data.CustomerDto;
import org.mapstruct.itest.kotlin.data.CustomerEntity;
import org.mapstruct.itest.kotlin.data.CustomerMapper;

public class KotlinDataTest {

    @Test
    public void shouldMapData() {
        CustomerEntity customer = CustomerMapper.INSTANCE.fromRecord( new CustomerDto( "Kermit", "kermit@test.com" ) );

        assertThat( customer ).isNotNull();
        assertThat( customer.getName() ).isEqualTo( "Kermit" );
        assertThat( customer.getMail() ).isEqualTo( "kermit@test.com" );
    }

    @Test
    public void shouldMapIntoData() {
        CustomerEntity entity = new CustomerEntity();
        entity.setName( "Kermit" );
        entity.setMail( "kermit@test.com" );

        CustomerDto customer = CustomerMapper.INSTANCE.toRecord( entity );

        assertThat( customer ).isNotNull();
        assertThat( customer.getName() ).isEqualTo( "Kermit" );
        assertThat( customer.getEmail() ).isEqualTo( "kermit@test.com" );
    }

    @Test
    public void shouldMapIntoDataWithOptionalParameters() {
        CustomerEntity entity = new CustomerEntity();
        entity.setName( "Kermit" );
        entity.setMail( "kermit@test.com" );

        CustomerWithOptionalParametersDto customer = CustomerMapper.INSTANCE.mapWithOptionalParameters( entity );

        assertThat( customer ).isNotNull();
        assertThat( customer.getName() ).isEqualTo( "Kermit" );
        assertThat( customer.getEmail() ).isEqualTo( "kermit@test.com" );
    }

    @Test
    public void shouldMapIntoDataWithMixedParameters() {
        CustomerEntity entity = new CustomerEntity();
        entity.setName( "Kermit" );
        entity.setAge( 42 );
        entity.setMail( "kermit@test.com" );
        entity.setJob( "Actor" );

        CustomerWithMixedParametersDto customer = CustomerMapper.INSTANCE.mapWithMixedParameters( entity );

        assertThat( customer ).isNotNull();
        assertThat( customer.getName() ).isEqualTo( "Kermit" );
        assertThat( customer.getAge() ).isEqualTo( 42 );
        assertThat( customer.getEmail() ).isEqualTo( "kermit@test.com" );
        assertThat( customer.getJob() ).isEqualTo( "Actor" );
    }
}
