/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.itest.java8;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Test for generation of Java8 based mapper implementations.
 *
 * @author Christian Schuster
 */
public class Java8MapperTest {

    @Test
    public void shouldMapWithRepeatedMappingAnnotation() {
        Java8Mapper mapper = Java8Mapper.INSTANCE;

        Source source = new Source();
        source.setFirstName( "firstname" );
        source.setLastName( "lastname" );

        Target target = mapper.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getGivenName() ).isEqualTo( source.getFirstName() );
        assertThat( target.getSurname() ).isEqualTo( source.getLastName() );
    }
}
