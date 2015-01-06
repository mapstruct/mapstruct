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
package org.mapstruct.ap.test.decorator;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Calendar;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for the application of decorators.
 *
 * @author Gunnar Morling
 */
@WithClasses({
    Person.class,
    PersonDto.class,
    Address.class,
    AddressDto.class
})
@IssueKey("163")
@RunWith(AnnotationProcessorTestRunner.class)
public class DecoratorTest {

    @Test
    @WithClasses({
        PersonMapper.class,
        PersonMapperDecorator.class
    })
    public void shouldInvokeDecoratorMethods() {
        //given
        Calendar birthday = Calendar.getInstance();
        birthday.set( 1928, 4, 23 );
        Person person = new Person( "Gary", "Crant", birthday.getTime(), new Address( "42 Ocean View Drive" ) );

        //when
        PersonDto personDto = PersonMapper.INSTANCE.personToPersonDto( person );

        //then
        assertThat( personDto ).isNotNull();
        assertThat( personDto.getName() ).isEqualTo( "Gary Crant" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
    }

    @Test
    @WithClasses({
        PersonMapper.class,
        PersonMapperDecorator.class
    })
    public void shouldDelegateNonDecoratedMethodsToDefaultImplementation() {
        //given
        Address address = new Address( "42 Ocean View Drive" );

        //when
        AddressDto addressDto = PersonMapper.INSTANCE.addressToAddressDto( address );

        //then
        assertThat( addressDto ).isNotNull();
        assertThat( addressDto.getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
    }

    @Test
    @WithClasses({
        AnotherPersonMapper.class,
        AnotherPersonMapperDecorator.class
    })
    public void shouldApplyDecoratorWithDefaultConstructor() {
        //given
        Calendar birthday = Calendar.getInstance();
        birthday.set( 1928, 4, 23 );
        Person person = new Person( "Gary", "Crant", birthday.getTime(), new Address( "42 Ocean View Drive" ) );

        //when
        PersonDto personDto = AnotherPersonMapper.INSTANCE.personToPersonDto( person );

        //then
        assertThat( personDto ).isNotNull();
        assertThat( personDto.getName() ).isEqualTo( "Gary Crant" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
    }

    @Test
    @WithClasses({
        YetAnotherPersonMapper.class,
        YetAnotherPersonMapperDecorator.class
    })
    public void shouldApplyDelegateToClassBasedMapper() {
        //given
        Calendar birthday = Calendar.getInstance();
        birthday.set( 1928, 4, 23 );
        Person person = new Person2( "Gary", "Crant", birthday.getTime(), new Address( "42 Ocean View Drive" ) );


        //when
        PersonDto personDto = YetAnotherPersonMapper.INSTANCE.personToPersonDto( person );

        //then
        assertThat( personDto ).isNotNull();
        assertThat( personDto.getName() ).isEqualTo( "Gary Crant" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
    }

    @IssueKey("173")
    @Test
    @WithClasses({
        Person2Mapper.class,
        Person2MapperDecorator.class,
        Person2.class,
        PersonDto2.class,
        Employer.class,
        EmployerDto.class,
        EmployerMapper.class,
        SportsClub.class,
        SportsClubDto.class
    })
    public void shouldApplyCustomMappers() {
        //given
        Calendar birthday = Calendar.getInstance();
        birthday.set( 1928, 4, 23 );
        Person2 person = new Person2( "Gary", "Crant", birthday.getTime(), new Address( "42 Ocean View Drive" ) );
        person.setEmployer( new Employer( "ACME" ) );
        person.setSportsClub( new SportsClub( "SC Duckburg" ) );

        //when
        PersonDto2 personDto = Person2Mapper.INSTANCE.personToPersonDto( person );

        //then
        assertThat( personDto ).isNotNull();
        assertThat( personDto.getName() ).isEqualTo( "Gary Crant" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
        assertThat( personDto.getEmployer() ).isNotNull();
        assertThat( personDto.getEmployer().getName() ).isNotNull();
        assertThat( personDto.getEmployer().getName() ).isEqualTo( "ACME" );
        assertThat( personDto.getSportsClub() ).isNotNull();
        assertThat( personDto.getSportsClub().getName() ).isNotNull();
        assertThat( personDto.getSportsClub().getName() ).isEqualTo( "SC Duckburg" );
    }

    @Test
    @WithClasses({
        ErroneousPersonMapper.class,
        ErroneousPersonMapperDecorator.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousPersonMapper.class,
                kind = Kind.ERROR,
                line = 27,
                messageRegExp = "Specified decorator type is no subtype of the annotated mapper type")
        }
    )
    public void shouldRaiseErrorInCaseWrongDecoratorTypeIsGiven() {

    }
}
