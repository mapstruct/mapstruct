/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.osgi_ds.constructor;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.decorator.Address;
import org.mapstruct.ap.test.decorator.AddressDto;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;
import org.mapstruct.ap.test.decorator.spring.constructor.PersonMapper;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Test for the application of decorators using component model osgi-ds.
 *
 */
@WithClasses({
    Person.class,
    PersonDto.class,
    Address.class,
    AddressDto.class,
    org.mapstruct.ap.test.decorator.osgi_ds.constructor.PersonMapper.class,
    PersonMapperDecorator.class
})
@IssueKey("2400")
@RunWith(AnnotationProcessorTestRunner.class)
@Component
public class OsgiDsDecoratorTest {

    @Reference
    private PersonMapper personMapper;

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Before
    public void osgiUp() {
        // TODO osgi
    }

    @After
    public void osgiDown() {
        // TODO osgi
    }

    @Test
    @Ignore("TODO: invoke osgi container somehow to see if DS annotations work as described in following test")
    public void shouldInvokeDecoratorMethods() {
        //given
        Calendar birthday = Calendar.getInstance();
        birthday.set( 1928, Calendar.MAY, 23 );
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
    @Ignore("TODO: invoke osgi container somehow to see if DS annotations work as described in following test")
    public void shouldDelegateNonDecoratedMethodsToDefaultImplementation() {
        //given
        Address address = new Address( "42 Ocean View Drive" );

        //when
        AddressDto addressDto = personMapper.addressToAddressDto( address );

        //then
        assertThat( addressDto ).isNotNull();
        assertThat( addressDto.getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
    }

    @Test
    public void shouldExtendDecoratorAndHaveConstructorInjection() {
        generatedSource.forMapper( org.mapstruct.ap.test.decorator.osgi_ds.constructor.PersonMapper.class )
                .content()
                .contains( "private final PersonMapper" )
                .contains( "@Component" + lineSeparator() +
                        "public class PersonMapperImpl extends PersonMapperDecorator" )
                .contains( "@Activate" + lineSeparator() +
                        "    public PersonMapperImpl(@Reference PersonMapper" );
    }
}
