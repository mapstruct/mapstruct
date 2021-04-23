/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.osgi_ds.compileoptionconstructor;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerRecordDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerRecordEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Test constructor injection for component model osgi-ds with
 * compile option mapstruct.defaultInjectStrategy=constructor
 *
 */
@WithClasses( {
    CustomerRecordDto.class,
    CustomerRecordEntity.class,
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerRecordOsgiDsCompileOptionConstructorMapper.class,
    CustomerOsgiDsCompileOptionConstructorMapper.class,
    GenderOsgiDsCompileOptionConstructorMapper.class
} )
@IssueKey("2400")
@RunWith(AnnotationProcessorTestRunner.class)
@ProcessorOption( name = "mapstruct.defaultInjectionStrategy", value = "constructor")
@Component
public class OsgiDsCompileOptionConstructorMapperTest {

    private static TimeZone originalTimeZone;

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Reference
    private CustomerRecordOsgiDsCompileOptionConstructorMapper customerRecordMapper;

    @BeforeClass
    public static void setDefaultTimeZoneToCet() {
        originalTimeZone = TimeZone.getDefault();
        TimeZone.setDefault( TimeZone.getTimeZone( "Europe/Berlin" ) );
    }

    @AfterClass
    public static void restoreOriginalTimeZone() {
        TimeZone.setDefault( originalTimeZone );
    }

    @Before
    public void osgiUp() {
        // TODO osgi
    }

    @After
    public void osgiDown() {
        // TODO osgi
    }

    @Test
    @Ignore("TODO osgi: invoke osgi container somehow to see if DS annotations work as described in following test")
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

    private Date createDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-M-yyyy hh:mm:ss" );
        return sdf.parse( date );
    }

    @Test
    public void shouldConstructorInjectionFromCompileOption() {
        generatedSource.forMapper( CustomerOsgiDsCompileOptionConstructorMapper.class )
            .content()
            .contains( "private final GenderOsgiDsCompileOptionConstructorMapper" )
            .contains( "@Component" + lineSeparator() +
               "public class CustomerOsgiDsCompileOptionConstructorMapperImpl" )
            .contains( "@Activate" + lineSeparator() +
                "    public CustomerOsgiDsCompileOptionConstructorMapperImpl" +
                                "(@Reference GenderOsgiDsCompileOptionConstructorMapper" );
    }
}
