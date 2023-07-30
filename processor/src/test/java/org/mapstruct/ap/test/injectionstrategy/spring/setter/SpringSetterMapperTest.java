/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithSpring;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test setter injection for component model spring.
 *
 * @author Lucas Resch
 */
@WithClasses( {
    CustomerRecordDto.class,
    CustomerRecordEntity.class,
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerRecordSpringSetterMapper.class,
    CustomerSpringSetterMapper.class,
    GenderSpringSetterMapper.class,
    SetterSpringConfig.class
} )
@IssueKey( "3229" )
@ComponentScan(basePackageClasses = CustomerSpringSetterMapper.class)
@Configuration
@WithSpring
@DefaultTimeZone("Europe/Berlin")
public class SpringSetterMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @Autowired
    private CustomerRecordSpringSetterMapper customerRecordMapper;
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
        assertThat( customerRecordDto.getRegistrationDate() ).hasToString( "1982-08-31T10:20:56.000+02:00" );
    }

    @ProcessorTest
    public void shouldHaveSetterInjection() {
        String method =  "@Autowired" + lineSeparator() +
            "    public void setGenderSpringSetterMapper(GenderSpringSetterMapper genderSpringSetterMapper) {" +
            lineSeparator() + "        this.genderSpringSetterMapper = genderSpringSetterMapper;" +
            lineSeparator() + "    }";
        generatedSource.forMapper( CustomerSpringSetterMapper.class )
            .content()
            .contains( "private GenderSpringSetterMapper genderSpringSetterMapper;" )
            .contains( method );
    }

    private Date createDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-M-yyyy hh:mm:ss" );
        return sdf.parse( date );
    }

}
