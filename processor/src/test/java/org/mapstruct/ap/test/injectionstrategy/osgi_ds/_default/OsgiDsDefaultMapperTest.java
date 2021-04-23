/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.osgi_ds._default;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.test.injectionstrategy.spring._default.CustomerSpringDefaultMapper;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Test field injection for component model osgi-ds.
 *
 */
@WithClasses({
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerOsgiDsDefaultMapper.class,
    GenderOsgiDsDefaultMapper.class
})
@IssueKey("2400")
@RunWith(AnnotationProcessorTestRunner.class)
@Component
public class OsgiDsDefaultMapperTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Reference
    private CustomerSpringDefaultMapper customerMapper;

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
    public void shouldHaveBeenDeclaredAsAComponent() {
        generatedSource.forMapper( CustomerOsgiDsDefaultMapper.class )
                .content()
                .contains( "@Component" + lineSeparator() + "public class CustomerOsgiDsDefaultMapperImpl" );
    }

    @Test
    public void shouldHaveFieldInjection() {
        generatedSource.forMapper( CustomerOsgiDsDefaultMapper.class )
            .content()
            .contains( "@Reference" + lineSeparator() + "    private GenderOsgiDsDefaultMapper" )
            .doesNotContain( "public CustomerOsgiDsDefaultMapperImpl(" );
    }
}
