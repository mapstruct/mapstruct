/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.dagger2.constructor;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test constructor injection for component model dagger2.
 */
@WithClasses({
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerDagger2ConstructorMapper.class,
    GenderDagger2ConstructorMapper.class,
    ConstructorDagger2Config.class
})
@ComponentScan(basePackageClasses = CustomerDagger2ConstructorMapper.class)
@Configuration
public class Dagger2ConstructorMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @Inject
    @Named
    private CustomerDagger2ConstructorMapper customerMapper;
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
    public void shouldConvertToTarget() {
        // given
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName( "Samuel" );
        customerEntity.setGender( Gender.MALE );

        // when
        CustomerDto customerDto = customerMapper.asTarget( customerEntity );

        // then
        assertThat( customerDto ).isNotNull();
        assertThat( customerDto.getName() ).isEqualTo( "Samuel" );
        assertThat( customerDto.getGender() ).isEqualTo( GenderDto.M );
    }

    @ProcessorTest
    public void shouldHaveConstructorInjection() {
        generatedSource.forMapper( CustomerDagger2ConstructorMapper.class )
            .content()
            .contains( "private final GenderDagger2ConstructorMapper" )
            .contains( "@Inject" + lineSeparator() +
                "    public CustomerDagger2ConstructorMapperImpl(GenderDagger2ConstructorMapper" );
    }
}
