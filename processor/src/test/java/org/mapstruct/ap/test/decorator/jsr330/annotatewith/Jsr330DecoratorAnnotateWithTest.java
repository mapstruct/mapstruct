/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.jsr330.annotatewith;

import java.util.Calendar;
import javax.inject.Inject;
import javax.inject.Named;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.decorator.Address;
import org.mapstruct.ap.test.decorator.AddressDto;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;
import org.mapstruct.ap.test.decorator.TestAnnotation;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJavaxInject;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the application of @AnnotateWith on decorator classes with JSR-330 component model.
 */
@IssueKey("3659")
@WithClasses({
    Person.class,
    PersonDto.class,
    Address.class,
    AddressDto.class,
    Jsr330AnnotateWithMapper.class,
    Jsr330AnnotateWithMapperDecorator.class,
    TestAnnotation.class
})
@ComponentScan(basePackageClasses = Jsr330DecoratorAnnotateWithTest.class)
@Configuration
@WithJavaxInject
@DisabledOnJre(JRE.OTHER)
public class Jsr330DecoratorAnnotateWithTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @Inject
    @Named
    private Jsr330AnnotateWithMapper jsr330AnnotateWithMapper;

    private ConfigurableApplicationContext context;

    @BeforeEach
    public void springUp() {
        context = new AnnotationConfigApplicationContext( getClass() );
        context.getAutowireCapableBeanFactory().autowireBean( this );
    }

    @AfterEach
    public void springDown() {
        if ( context != null ) {
            context.close();
        }
    }

    @ProcessorTest
    public void shouldContainCustomAnnotation() {
        generatedSource.forMapper( Jsr330AnnotateWithMapper.class )
            .content()
            .contains( "@TestAnnotation" );
    }

    @ProcessorTest
    public void shouldInvokeDecoratorMethods() {
        Calendar birthday = Calendar.getInstance();
        birthday.set( 1928, Calendar.MAY, 23 );
        Person person = new Person( "Gary", "Crant", birthday.getTime(), new Address( "42 Ocean View Drive" ) );

        PersonDto personDto = jsr330AnnotateWithMapper.personToPersonDto( person );

        assertThat( personDto ).isNotNull();
        assertThat( personDto.getName() ).isEqualTo( "Gary Crant" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
    }
}
