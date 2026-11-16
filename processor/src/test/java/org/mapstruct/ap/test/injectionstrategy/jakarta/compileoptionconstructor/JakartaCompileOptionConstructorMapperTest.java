/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.jakarta.compileoptionconstructor;

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
import org.mapstruct.ap.testutil.WithJakartaInject;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test constructor injection for component model jakarta with compile option
 * mapstruct.defaultInjectionStrategy=constructor
 *
 * @author Filip Hrisafov
 */
@IssueKey("2567")
@WithClasses({
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerJakartaCompileOptionConstructorMapper.class,
    GenderJakartaCompileOptionConstructorMapper.class
})
@ProcessorOption(name = "mapstruct.defaultInjectionStrategy", value = "constructor")
@ComponentScan(basePackageClasses = CustomerJakartaCompileOptionConstructorMapper.class)
@Configuration
@WithJakartaInject
public class JakartaCompileOptionConstructorMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @Autowired
    private CustomerJakartaCompileOptionConstructorMapper customerMapper;
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
    public void shouldHaveJakartaInjection() {
        generatedSource.forMapper( CustomerJakartaCompileOptionConstructorMapper.class )
            .content()
            .contains( "import jakarta.inject.Inject;" )
            .contains( "import jakarta.inject.Named;" )
            .contains( "import jakarta.inject.Singleton;" )
            .contains( "private final GenderJakartaCompileOptionConstructorMapper" )
            .contains( "@Inject" + lineSeparator() +
                "    public CustomerJakartaCompileOptionConstructorMapperImpl" +
                "(GenderJakartaCompileOptionConstructorMapper" )
            .doesNotContain( "javax.inject" );
    }
}
