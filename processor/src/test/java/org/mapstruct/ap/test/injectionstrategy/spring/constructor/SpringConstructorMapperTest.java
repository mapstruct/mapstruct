/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.constructor;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junitpioneer.jupiter.DefaultTimeZone;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerRecordDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerRecordEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithSpring;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Test constructor injection for component model spring.
 *
 * @author Kevin Grüneberg
 */
@WithClasses( {
    CustomerRecordDto.class,
    CustomerRecordEntity.class,
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerRecordSpringConstructorMapper.class,
    CustomerSpringConstructorMapper.class,
    GenderSpringConstructorMapper.class,
    ConstructorSpringConfig.class
} )
@IssueKey( "571" )
@ComponentScan(basePackageClasses = CustomerSpringConstructorMapper.class)
@Configuration
@WithSpring
@DefaultTimeZone("Europe/Berlin")
public class SpringConstructorMapperTest {

    private static TimeZone originalTimeZone;

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @Autowired
    private CustomerRecordSpringConstructorMapper customerRecordMapper;
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
    public void shouldConvertToTarget() throws Exception {
        // given
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName( "Samuel" );
        customerEntity.setGender( Gender.MALE );
        CustomerRecordEntity customerRecordEntity = new CustomerRecordEntity();
        customerRecordEntity.setCustomer( customerEntity );
        customerRecordEntity.setRegistrationDate( createDate( "31-08-1982 10:20:56" ) );

        // when
        CustomerRecordDto customerRecordDto = customerRecordMapper.asTarget( customerRecordEntity );

        // then
        assertThat( customerRecordDto ).isNotNull();
        assertThat( customerRecordDto.getCustomer() ).isNotNull();
        assertThat( customerRecordDto.getCustomer().getName() ).isEqualTo( "Samuel" );
        assertThat( customerRecordDto.getCustomer().getGender() ).isEqualTo( GenderDto.M );
        assertThat( customerRecordDto.getRegistrationDate() ).isNotNull();
        assertThat( customerRecordDto.getRegistrationDate().toString() ).isEqualTo( "1982-08-31T10:20:56.000+02:00" );
    }

    @ProcessorTest
    public void shouldHaveConstructorInjection() {
        generatedSource.forMapper( CustomerSpringConstructorMapper.class )
            .content()
            .contains( "private final GenderSpringConstructorMapper" )
            .contains( "@Autowired" + lineSeparator() +
                "    public CustomerSpringConstructorMapperImpl(GenderSpringConstructorMapper" );
    }

    private Date createDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-M-yyyy hh:mm:ss" );
        return sdf.parse( date );
    }

}
