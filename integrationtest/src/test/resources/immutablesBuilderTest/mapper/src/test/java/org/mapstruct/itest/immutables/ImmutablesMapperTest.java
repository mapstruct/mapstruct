/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for generation of org.immutables Mapper implementations
 *
 * Handles the Top Level and No Style case which is the out-of-the-box default
 * most basic setup with org.immutables.
 *
 * The generated classes are named using the default style of Immutable*
 * Coverage for other variants elsewhere
 *
 * @author Eric Martineau
 */
public class ImmutablesMapperTest {

    @Test
    public void fromImmutableToPojo() {
        PersonDto personDto = PersonMapper.INSTANCE.toDto( ImmutablePerson.builder()
            .age( 33 )
            .name( "Bob" )
            .address( ImmutableAddress.builder()
                .addressLine( "Wild Drive" )
                .build() )
            .build() );
        assertThat( personDto.getAge() ).isEqualTo( 33 );
        assertThat( personDto.getName() ).isEqualTo( "Bob" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "Wild Drive" );
    }

    @Test
    public void fromPojoToImmutable() {
        Person person = PersonMapper.INSTANCE.fromDto( new PersonDto( "Bob", 33, new AddressDto( "Wild Drive" ) ) );
        assertThat( person.getAge() ).isEqualTo( 33 );
        assertThat( person.getName() ).isEqualTo( "Bob" );
        assertThat( person.getAddress() ).isNotNull();
        assertThat( person.getAddress().getAddressLine() ).isEqualTo( "Wild Drive" );
    }
}
