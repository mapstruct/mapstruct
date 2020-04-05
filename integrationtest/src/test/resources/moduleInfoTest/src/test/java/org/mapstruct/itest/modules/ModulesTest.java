/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.modules;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mapstruct.itest.modules.CustomerDto;
import org.mapstruct.itest.modules.CustomerEntity;
import org.mapstruct.itest.modules.CustomerMapper;

public class ModulesTest {

    @Test
    public void shouldMapRecord() {
        CustomerDto dto = new CustomerDto();
        dto.setName( "Kermit" );
        dto.setEmail( "kermit@test.com" );
        CustomerEntity customer = CustomerMapper.INSTANCE.fromDto( dto );

        assertThat( customer ).isNotNull();
        assertThat( customer.getName() ).isEqualTo( "Kermit" );
        assertThat( customer.getMail() ).isEqualTo( "kermit@test.com" );
    }
}
