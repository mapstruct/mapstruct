/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.records.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.itest.records.api.CustomerDto;
import org.mapstruct.itest.records.mapper.CustomerEntity;
import org.mapstruct.itest.records.mapper.CustomerMapper;

import static org.assertj.core.api.Assertions.assertThat;

class RecordsTest {

    @Test
    void shouldMapRecord() {
        CustomerEntity customer = CustomerMapper.INSTANCE.fromRecord( new CustomerDto( "Kermit", "kermit@test.com" ) );

        assertThat( customer ).isNotNull();
        assertThat( customer.getName() ).isEqualTo( "Kermit" );
        assertThat( customer.getMail() ).isEqualTo( "kermit@test.com" );
    }

    @Test
    void shouldMapIntoRecord() {
        CustomerEntity entity = new CustomerEntity();
        entity.setName( "Kermit" );
        entity.setMail( "kermit@test.com" );

        CustomerDto customer = CustomerMapper.INSTANCE.toRecord( entity );

        assertThat( customer ).isNotNull();
        assertThat( customer.name() ).isEqualTo( "Kermit" );
        assertThat( customer.email() ).isEqualTo( "kermit@test.com" );
    }

}
