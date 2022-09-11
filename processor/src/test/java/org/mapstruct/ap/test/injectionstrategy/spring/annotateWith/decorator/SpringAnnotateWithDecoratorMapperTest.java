/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.annotateWith.decorator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
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
 * Test the behavior of decorators when using AnnotateWith and Spring component model.
 *
 * @author Jose Carlos Campanero Ortiz
 */
@WithClasses({
        CustomerDto.class,
        CustomerEntity.class,
        Gender.class,
        GenderDto.class,
        CustomerSpringComponentQualifiedMapper.class,
        UpperCaseCustomerNameSpringDefaultMapperDecorator.class,
        CustomerSpringDefaultMapper.class,
        GenderSpringDefaultMapper.class
})
@IssueKey( "1427" )
@ComponentScan(basePackageClasses = CustomerSpringDefaultMapper.class)
@Configuration
@WithSpring
public class SpringAnnotateWithDecoratorMapperTest {

    @Autowired
    private CustomerSpringDefaultMapper customerMapper;

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();
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
    public void shouldBehaveCorrectly() {
        // given
        final String name = "Mary Smith";
        final Gender gender = Gender.FEMALE;
        final CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName( name );
        customerEntity.setGender( gender );

        // when
        final CustomerDto customerDto = customerMapper.asTarget( customerEntity );

        // then
        assertThat( customerMapper ).isNotNull();
        assertThat( customerDto ).isNotNull();
        assertThat( customerDto.getName() ).isNotEmpty();
        assertThat( customerDto.getName() ).isEqualTo( name.toUpperCase() );
    }

}
