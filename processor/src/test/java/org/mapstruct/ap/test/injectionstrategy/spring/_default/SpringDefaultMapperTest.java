/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring._default;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test field injection for component model spring.
 *
 * @author Filip Hrisafov
 */
@WithClasses({
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerSpringDefaultMapper.class,
    GenderSpringDefaultMapper.class
})
@IssueKey("571")
@RunWith(AnnotationProcessorTestRunner.class)
@ComponentScan(basePackageClasses = CustomerSpringDefaultMapper.class)
@Configuration
public class SpringDefaultMapperTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Autowired
    private CustomerSpringDefaultMapper customerMapper;
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
        generatedSource.forMapper( CustomerSpringDefaultMapper.class )
            .content()
            .contains( "@Autowired" + lineSeparator() + "    private GenderSpringDefaultMapper" )
            .doesNotContain( "public CustomerSpringDefaultMapperImpl(" );
    }
}
