/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.spring.constructor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Test for the application of decorators using component model spring.
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
@ComponentScan(basePackageClasses = SpringDecoratorTest.class)
@Configuration
public class SpringDecoratorTest {

    @Autowired
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
