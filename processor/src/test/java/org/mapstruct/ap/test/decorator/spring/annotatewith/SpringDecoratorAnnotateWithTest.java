/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.spring.annotatewith;

import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.decorator.Address;
import org.mapstruct.ap.test.decorator.AddressDto;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithSpring;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the application of @AnnotateWith on decorator classes with Spring component model.
 */
@IssueKey("3659")
@WithClasses({
    Person.class,
    PersonDto.class,
    Address.class,
    AddressDto.class,
    AnnotateMapper.class,
    AnnotateMapperDecorator.class,
    CustomComponent.class,
    CustomPrimary.class,
    CustomAnnotateMapper.class,
    CustomAnnotateMapperDecorator.class
})
@WithSpring
@ComponentScan(basePackageClasses = SpringDecoratorAnnotateWithTest.class)
@Configuration
public class SpringDecoratorAnnotateWithTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @Autowired
    private AnnotateMapper annotateMapper;

    @Autowired
    private CustomAnnotateMapper customAnnotateMapper;

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
    public void shouldNotDuplicateComponentAnnotation() {
        generatedSource.forMapper( AnnotateMapper.class )
            .content()
            .contains( "@Component(value = \"decoratorComponent\")", "@Primary" )
            .doesNotContain( "@Component" + System.lineSeparator() );
    }

    @ProcessorTest
    public void shouldNotDuplicateCustomComponentAnnotation() {
        generatedSource.forMapper( CustomAnnotateMapper.class )
            .content()
            .contains( "@CustomComponent(value = \"customComponentDecorator\")", "@CustomPrimary" )
            .doesNotContain( "@Component" );
    }

    @ProcessorTest
    public void shouldInvokeAnnotateDecoratorMethods() {
        Calendar birthday = Calendar.getInstance();
        birthday.set( 1928, Calendar.MAY, 23 );
        Person person = new Person( "Gary", "Crant", birthday.getTime(), new Address( "42 Ocean View Drive" ) );

        PersonDto personDto = annotateMapper.personToPersonDto( person );

        assertThat( personDto ).isNotNull();
        assertThat( personDto.getName() ).isEqualTo( "Gary Crant" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
    }

    @ProcessorTest
    public void shouldInvokeCustomAnnotateDecoratorMethods() {
        Calendar birthday = Calendar.getInstance();
        birthday.set( 1928, Calendar.MAY, 23 );
        Person person = new Person( "Gary", "Crant", birthday.getTime(), new Address( "42 Ocean View Drive" ) );

        PersonDto personDto = customAnnotateMapper.personToPersonDto( person );

        assertThat( personDto ).isNotNull();
        assertThat( personDto.getName() ).isEqualTo( "Gary Crant" );
        assertThat( personDto.getAddress() ).isNotNull();
        assertThat( personDto.getAddress().getAddressLine() ).isEqualTo( "42 Ocean View Drive" );
    }
}
