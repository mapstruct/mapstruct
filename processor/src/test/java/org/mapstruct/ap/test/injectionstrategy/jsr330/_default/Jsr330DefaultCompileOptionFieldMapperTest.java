/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.jsr330._default;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test field injection for component model jsr330.
 * Default value option mapstruct.defaultInjectionStrategy is "field"
 *
 * @author Andrei Arlou
 */
@WithClasses({
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerJsr330DefaultCompileOptionFieldMapper.class,
    GenderJsr330DefaultCompileOptionFieldMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@ComponentScan(basePackageClasses = CustomerJsr330DefaultCompileOptionFieldMapper.class)
@Configuration
public class Jsr330DefaultCompileOptionFieldMapperTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Inject
    @Named
    private CustomerJsr330DefaultCompileOptionFieldMapper customerMapper;
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

    @Test
    public void shouldHaveFieldInjection() {
        generatedSource.forMapper( CustomerJsr330DefaultCompileOptionFieldMapper.class )
            .content()
            .contains( "@Inject" + lineSeparator() + "    private GenderJsr330DefaultCompileOptionFieldMapper" )
            .doesNotContain( "public CustomerJsr330DefaultCompileOptionFieldMapperImpl(" );
    }
}
