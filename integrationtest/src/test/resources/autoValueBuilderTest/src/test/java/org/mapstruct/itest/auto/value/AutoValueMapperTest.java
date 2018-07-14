/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.auto.value;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for generation of Lombok Builder Mapper implementations
 *
 * @author Eric Martineau
 */
public class AutoValueMapperTest {

    @Test
    public void testSimpleImmutableBuilderHappyPath() {
        PersonDto personDto = PersonMapper.INSTANCE.toDto( Person.builder()
            .age( 33 )
            .name( "Bob" )
            .address( Address.builder()
                .addressLine( "Wild Drive" )
                .build() )
            .build() );
        assertThat( personDto.getAge() ).isEqualTo( 33 );
        assertThat( personDto.getName() ).isEqualTo( "Bob" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "Wild Drive" );
    }

    @Test
    public void testLombokToImmutable() {
        Person person = PersonMapper.INSTANCE.fromDto( new PersonDto( "Bob", 33, new AddressDto( "Wild Drive" ) ) );
        assertThat( person.getAge() ).isEqualTo( 33 );
        assertThat( person.getName() ).isEqualTo( "Bob" );
        assertThat( person.getAddress() ).isNotNull();
        assertThat( person.getAddress().getAddressLine() ).isEqualTo( "Wild Drive" );
    }
}
