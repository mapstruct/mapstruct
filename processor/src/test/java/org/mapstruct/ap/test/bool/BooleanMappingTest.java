/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bool;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

@WithClasses({
    Person.class,
    PersonDto.class,
    YesNo.class,
    PersonMapper.class,
    YesNoMapper.class
})
public class BooleanMappingTest {

    @ProcessorTest
    public void shouldMapBooleanPropertyWithIsPrefixedGetter() {
        //given
        Person person = new Person();
        person.setMarried( Boolean.TRUE );

        //when
        PersonDto personDto = PersonMapper.INSTANCE.personToDto( person );

        //then
        assertThat( personDto.getMarried() ).isEqualTo( "true" );
    }

    @ProcessorTest
    public void shouldMapBooleanPropertyPreferringGetPrefixedGetterOverIsPrefixedGetter() {
        //given
        Person person = new Person();
        person.setEngaged( Boolean.TRUE );

        //when
        PersonDto personDto = PersonMapper.INSTANCE.personToDto( person );

        //then
        assertThat( personDto.getEngaged() ).isEqualTo( "true" );
    }

    @ProcessorTest
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
