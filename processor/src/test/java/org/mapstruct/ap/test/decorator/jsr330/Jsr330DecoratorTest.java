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
package org.mapstruct.ap.test.decorator.jsr330;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.decorator.Address;
import org.mapstruct.ap.test.decorator.AddressDto;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test for the application of decorators using component model jsr330.
 *
 * @author Andreas Gudian
 */
@WithClasses({
    Person.class,
    PersonDto.class,
    Address.class,
    AddressDto.class,
    PersonMapper.class,
    PersonMapperDecorator.class
})
@IssueKey("592")
@RunWith(AnnotationProcessorTestRunner.class)
@ComponentScan(basePackageClasses = Jsr330DecoratorTest.class)
@Configuration
public class Jsr330DecoratorTest {

    @Inject
    @Named
    private PersonMapper personMapper;
    private ConfigurableApplicationContext context;

    @Before
    public void springUp() {
        context = new AnnotationConfigApplicationContext( getClass() );
        context.getAutowireCapableBeanFactory().autowireBean( this );
    }

    @After
    public void springDown() {
        if ( context != null ) {
            context.close();
        }
    }

    @Test
    public void shouldInvokeDecoratorMethods() {
        //given
        Calendar birthday = Calendar.getInstance();
        birthday.set( 1928, 4, 23 );
        Person person = new Person( "Gary", "Crant", birthday.getTime(), new Address( "42 Ocean View Drive" ) );

        //when
        PersonDto personDto = personMapper.personToPersonDto( person );

        //then
        assertThat( personDto ).isNotNull();
        assertThat( personDto.getName() ).isEqualTo( "Gary Crant" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
    }

    @Test
    public void shouldDelegateNonDecoratedMethodsToDefaultImplementation() {
        //given
        Address address = new Address( "42 Ocean View Drive" );

        //when
        AddressDto addressDto = personMapper.addressToAddressDto( address );

        //then
        assertThat( addressDto ).isNotNull();
        assertThat( addressDto.getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
    }
}
