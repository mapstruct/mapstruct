/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.annotateWith;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithSpring;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test custom component annotation placement.
 *
 * @author Ben Zegveld
 */
@WithClasses({
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerSpringComponentQualifiedMapper.class,
    CustomerSpringDefaultMapper.class,
    GenderSpringDefaultMapper.class
})
@IssueKey( "1427" )
@ComponentScan(basePackageClasses = CustomerSpringDefaultMapper.class)
@Configuration
@WithSpring
public class SpringAnnotateWithMapperTest {

    @Autowired
    @Qualifier( "AnnotateWith" )
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
    public void shouldHaveQualifiedMapper() {

        // then
        assertThat( customerMapper ).isNotNull();
        generatedSource.forMapper( CustomerSpringComponentQualifiedMapper.class )
                       .content()
                       .contains( "@Component(\"AnnotateWith\")" )
                       .doesNotContain( "@Component" + System.lineSeparator() );
    }
}
