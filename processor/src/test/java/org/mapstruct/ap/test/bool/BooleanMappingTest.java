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
package org.mapstruct.ap.test.bool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

@WithClasses({
    Person.class,
    PersonDto.class,
    YesNo.class,
    PersonMapper.class,
    YesNoMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class BooleanMappingTest {

    @Test
    public void shouldMapBooleanPropertyWithIsPrefixedGetter() {
        //given
        Person person = new Person();
        person.setMarried( Boolean.TRUE );

        //when
        PersonDto personDto = PersonMapper.INSTANCE.personToDto( person );

        //then
        assertThat( personDto.getMarried() ).isEqualTo( "true" );
    }

    @Test
    public void shouldMapBooleanPropertyPreferringGetPrefixedGetterOverIsPrefixedGetter() {
        //given
        Person person = new Person();
        person.setEngaged( Boolean.TRUE );

        //when
        PersonDto personDto = PersonMapper.INSTANCE.personToDto( person );

        //then
        assertThat( personDto.getEngaged() ).isEqualTo( "true" );
    }

    @Test
    public void shouldMapBooleanPropertyWithPropertyMappingMethod() {
        // given
        Person person = new Person();
        person.setDivorced( new YesNo( true ) );
        person.setWidowed( new YesNo( true ) );

        // when
        PersonDto personDto = PersonMapper.INSTANCE.personToDto( person );

        // then
        assertThat( personDto.getDivorced() ).isEqualTo( "yes" );
        assertThat( personDto.getWidowed() ).isEqualTo( Boolean.TRUE );
    }
}
