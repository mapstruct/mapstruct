/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.dagger2.emptyconstructor;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test constructor injection for component model dagger2, when a mapper has no extra dependencies.
 */
@WithClasses({
    Gender.class,
    GenderDto.class,
    ConstructorDagger2Config.class,
    GenderDagger2EmptyConstructorMapper.class
})
@ComponentScan(basePackageClasses = GenderDagger2EmptyConstructorMapper.class)
@Configuration
public class Dagger2EmptyConstructorMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @Inject
    @Named
    private GenderDagger2EmptyConstructorMapper customerMapper;
    private ConfigurableApplicationContext context;

    @BeforeEach
    public void springUp() {
        context = new AnnotationConfigApplicationContext( getClass() );
        context.getAutowireCapableBeanFactory().autowireBean( this );

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
    }

    @AfterEach
    public void springDown() {
        if ( context != null ) {
            context.close();
        }
    }

    @ProcessorTest
    public void shouldMapToDto() {
        // given
        Gender gender = Gender.MALE;

        // when
        GenderDto genderDto = customerMapper.mapToDto( gender );

        // then
        assertThat( genderDto ).isEqualTo( GenderDto.M );
    }

    @ProcessorTest
    public void shouldHaveConstructorInjection() {
        generatedSource.forMapper( GenderDagger2EmptyConstructorMapper.class )
            .content()
            .contains( "@Inject" + lineSeparator() + "    public GenderDagger2EmptyConstructorMapperImpl()" );
    }
}
