/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.jsr330.compileoptionconstructor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJavaxInject;
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
 * Test constructor injection for component model jsr330 with compile option
 * mapstruct.defaultInjectionStrategy=constructor
 *
 * @author Andrei Arlou
 */
@WithClasses({
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerJsr330CompileOptionConstructorMapper.class,
    GenderJsr330CompileOptionConstructorMapper.class
})
@ProcessorOption( name = "mapstruct.defaultInjectionStrategy", value = "constructor")
@ComponentScan(basePackageClasses = CustomerJsr330CompileOptionConstructorMapper.class)
@Configuration
@WithJavaxInject
@DisabledOnJre(JRE.OTHER)
public class Jsr330CompileOptionConstructorMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @Autowired
    private CustomerJsr330CompileOptionConstructorMapper customerMapper;
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
    public void shouldHaveConstructorInjectionFromCompileOption() {
        generatedSource.forMapper( CustomerJsr330CompileOptionConstructorMapper.class )
            .content()
            .contains( "import javax.inject.Inject;" )
            .contains( "import javax.inject.Named;" )
            .contains( "import javax.inject.Singleton;" )
            .contains( "private final GenderJsr330CompileOptionConstructorMapper" )
            .contains( "@Inject" + lineSeparator() +
                "    public CustomerJsr330CompileOptionConstructorMapperImpl" +
                            "(GenderJsr330CompileOptionConstructorMapper" );
    }
}
