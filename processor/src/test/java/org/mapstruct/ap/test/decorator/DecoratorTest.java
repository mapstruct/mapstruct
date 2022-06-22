/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

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
public class DecoratorTest {

    @ProcessorTest
    @WithClasses({
        PersonMapper.class,
        PersonMapperDecorator.class
    })
    public void shouldInvokeDecoratorMethods() {
        //given
        Calendar birthday = Calendar.getInstance();
        birthday.set( 1928, Calendar.MAY, 23 );
        Person person = new Person( "Gary", "Crant", birthday.getTime(), new Address( "42 Ocean View Drive" ) );

        //when
        PersonDto personDto = PersonMapper.INSTANCE.personToPersonDto( person );

        //then
        assertThat( personDto ).isNotNull();
        assertThat( personDto.getName() ).isEqualTo( "Gary Crant" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
    }

    @ProcessorTest
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

    @ProcessorTest
    @WithClasses({
        PersonMapper.class,
        PersonMapperDecorator.class
    })
    @IssueKey("765")
    public void shouldDelegateNonDecoratedVoidMethodsToDefaultImplementation() {
        //given
        AddressDto addressDto = new AddressDto( "42 Ocean View Drive" );

        //when
        Address address = new Address( "Main Street" );
        PersonMapper.INSTANCE.updateAddressFromDto( addressDto, address );

        //then
        assertThat( address.getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
    }

    @ProcessorTest
    @WithClasses({
        AnotherPersonMapper.class,
        AnotherPersonMapperDecorator.class
    })
    public void shouldApplyDecoratorWithDefaultConstructor() {
        //given
        Calendar birthday = Calendar.getInstance();
        birthday.set( 1928, Calendar.MAY, 23 );
        Person person = new Person( "Gary", "Crant", birthday.getTime(), new Address( "42 Ocean View Drive" ) );

        //when
        PersonDto personDto = AnotherPersonMapper.INSTANCE.personToPersonDto( person );

        //then
        assertThat( personDto ).isNotNull();
        assertThat( personDto.getName() ).isEqualTo( "Gary Crant" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
    }

    @ProcessorTest
    @WithClasses({
        YetAnotherPersonMapper.class,
        YetAnotherPersonMapperDecorator.class
    })
    public void shouldApplyDelegateToClassBasedMapper() {
        //given
        Calendar birthday = Calendar.getInstance();
        birthday.set( 1928, Calendar.MAY, 23 );
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
    @ProcessorTest
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
        birthday.set( 1928, Calendar.MAY, 23 );
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

    @ProcessorTest
    @WithClasses({
        ErroneousPersonMapper.class,
        ErroneousPersonMapperDecorator.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousPersonMapper.class,
                kind = Kind.ERROR,
                line = 14,
                message = "Specified decorator type is no subtype of the annotated mapper type.")
        }
    )
    public void shouldRaiseErrorInCaseWrongDecoratorTypeIsGiven() {

    }
}
