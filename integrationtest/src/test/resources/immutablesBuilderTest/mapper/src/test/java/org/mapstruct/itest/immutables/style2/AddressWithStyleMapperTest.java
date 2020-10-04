/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.style2;

import org.junit.Before;
import org.junit.Test;
import org.mapstruct.itest.immutables.AddressDto;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressWithStyleMapperTest {
    private final AddressDto dto = new AddressDto();

    private final AddressWithStyle domain = AddressWithStyle.builder().addressLine( "line" ).build();

    @Before
    public void before() {
        dto.setAddressLine( "line" );
    }

    @Test
    public void toImmutable() {
        AddressWithStyle actual = AddressWithStyleMapper.INSTANCE.fromDto( dto );
        assertThat( actual.getAddressLine() ).isEqualTo( "line" );
    }

    @Test
    public void fromImmutable() {
        AddressDto actual = AddressWithStyleMapper.INSTANCE.toDto( domain );
        assertThat( actual.getAddressLine() ).isEqualTo( "line" );
    }
}
