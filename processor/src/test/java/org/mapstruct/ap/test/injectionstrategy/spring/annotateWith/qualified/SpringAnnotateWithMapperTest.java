/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.annotateWith.qualified;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
        CustomerSpringComponentQualifiedMapper.class,
        CustomerSpringServiceQualifiedMapper.class,
        CustomerSpringDefaultMapper.class,
        GenderSpringDefaultMapper.class
})
@IssueKey( "1427" )
@ComponentScan(basePackageClasses = CustomerSpringDefaultMapper.class)
@Configuration
@WithSpring
public class SpringAnnotateWithMapperTest {

    @Autowired
    @Qualifier( "AnnotateWithComponent" )
    private CustomerSpringDefaultMapper annotateWithComponentCustomerMapper;

    @Autowired
    @Qualifier( "AnnotateWithService" )
    private CustomerSpringDefaultMapper annotateWithServiceCustomerMapper;

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
    public void shouldHaveComponentAnnotatedQualifiedMapper() {

        // then
        assertThat( annotateWithComponentCustomerMapper ).isNotNull();
        generatedSource.forMapper( CustomerSpringComponentQualifiedMapper.class )
                .content()
                .contains( "@Component(value = \"AnnotateWithComponent\")" )
                .doesNotContain( "@Component" + System.lineSeparator() );

    }

    @ProcessorTest
    public void shouldHaveServiceAnnotatedQualifiedMapper() {

        // then
        assertThat( annotateWithServiceCustomerMapper ).isNotNull();
        generatedSource.forMapper( CustomerSpringServiceQualifiedMapper.class )
                .content()
                .contains( "@Service(value = \"AnnotateWithService\")" )
                .doesNotContain( "@Component" + System.lineSeparator() );

    }

}
