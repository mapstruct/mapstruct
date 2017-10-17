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
package org.mapstruct.itest.lombok;

import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for generation of Spring-based Mapper implementations
 *
 * @author Andreas Gudian
 */
public class LombokMapperTest {

    @Test
    public void testSimpleImmutableBuilderHappyPath() {
        final PersonMapper mapper = Mappers.getMapper( PersonMapper.class );
        final PersonDto personDto = mapper.toDto( Person.foo()
            .age( 33 )
            .name( "Bob" )
            .create() );
        assertThat( personDto.getAge() ).isEqualTo( 33 );
        assertThat( personDto.getName() ).isEqualTo( "Bob" );
    }

    @Test
    public void testLombokToImmutable() {
        final PersonMapper mapper = Mappers.getMapper( PersonMapper.class );
        final Person person = mapper.fromDto( new PersonDto( "Bob", 33) );
        assertThat( person.getAge() ).isEqualTo( 33 );
        assertThat( person.getName() ).isEqualTo( "Bob" );
    }
}
