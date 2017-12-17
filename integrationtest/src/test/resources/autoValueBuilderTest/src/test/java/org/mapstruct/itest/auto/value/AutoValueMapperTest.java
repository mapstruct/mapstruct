/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
