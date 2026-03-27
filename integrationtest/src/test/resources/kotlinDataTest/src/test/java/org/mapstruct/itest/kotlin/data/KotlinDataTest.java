/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.kotlin.data;

import org.junit.jupiter.api.Test;
import org.mapstruct.itest.kotlin.data.CustomerDto;
import org.mapstruct.itest.kotlin.data.CustomerEntity;
import org.mapstruct.itest.kotlin.data.CustomerMapper;

import static org.assertj.core.api.Assertions.assertThat;

class KotlinDataTest {

    @Test
    void shouldMapData() {
        CustomerEntity customer = CustomerMapper.INSTANCE.fromRecord( new CustomerDto( "Kermit", "kermit@test.com" ) );

        assertThat( customer ).isNotNull();
        assertThat( customer.getName() ).isEqualTo( "Kermit" );
        assertThat( customer.getMail() ).isEqualTo( "kermit@test.com" );
    }

    @Test
    void shouldMapIntoData() {
        CustomerEntity entity = new CustomerEntity();
        entity.setName( "Kermit" );
        entity.setMail( "kermit@test.com" );

        CustomerDto customer = CustomerMapper.INSTANCE.toRecord( entity );

        assertThat( customer ).isNotNull();
        assertThat( customer.getName() ).isEqualTo( "Kermit" );
        assertThat( customer.getEmail() ).isEqualTo( "kermit@test.com" );
    }
}
