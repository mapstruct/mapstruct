/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.kotlin.data;

import org.junit.jupiter.api.Nested;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithKotlinSources;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
class KotlinDataTest {

    @Nested
    @WithClasses({
        CustomerEntity.class,
        CustomerMapper.class
    })
    @WithKotlinSources("CustomerDto.kt")
    class Standard {

        @ProcessorTest
        void shouldMapData() {
            CustomerEntity customer = CustomerMapper.INSTANCE.fromData( new CustomerDto(
                "Kermit",
                "kermit@test.com"
            ) );

            assertThat( customer ).isNotNull();
            assertThat( customer.getName() ).isEqualTo( "Kermit" );
            assertThat( customer.getMail() ).isEqualTo( "kermit@test.com" );
        }

        @ProcessorTest
        void shouldMapIntoData() {
            CustomerEntity entity = new CustomerEntity();
            entity.setName( "Kermit" );
            entity.setMail( "kermit@test.com" );

            CustomerDto customer = CustomerMapper.INSTANCE.toData( entity );

            assertThat( customer ).isNotNull();
            assertThat( customer.getName() ).isEqualTo( "Kermit" );
            assertThat( customer.getEmail() ).isEqualTo( "kermit@test.com" );
        }

    }
}
