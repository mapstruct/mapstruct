/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.protobuf;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for generation of Protobuf Builder Mapper implementations
 *
 * @author Christian Bandowski
 */
public class ProtobufMapperTest {

    @Test
    public void testSimpleImmutableBuilderHappyPath() {
        PersonDto personDto = PersonMapper.INSTANCE.toDto( PersonProtos.Person.newBuilder()
            .setAge( 33 )
            .setName( "Bob" )
            .setAddress( PersonProtos.Person.Address.newBuilder()
                .setAddressLine( "Wild Drive" )
                .build() )
            .build() );

        assertThat( personDto.getAge() ).isEqualTo( 33 );
        assertThat( personDto.getName() ).isEqualTo( "Bob" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "Wild Drive" );
    }

    @Test
    public void testLombokToImmutable() {
        PersonProtos.Person person = PersonMapper.INSTANCE.fromDto( new PersonDto( "Bob", 33, new AddressDto( "Wild Drive" ) ) );

        assertThat( person.getAge() ).isEqualTo( 33 );
        assertThat( person.getName() ).isEqualTo( "Bob" );
        assertThat( person.getAddress() ).isNotNull();
        assertThat( person.getAddress().getAddressLine() ).isEqualTo( "Wild Drive" );
    }
}
