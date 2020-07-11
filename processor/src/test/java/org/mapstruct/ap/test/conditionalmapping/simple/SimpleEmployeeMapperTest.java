/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditionalmapping.simple;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.conditionalmapping.Employee;
import org.mapstruct.ap.test.conditionalmapping.EmployeeDto;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
        Employee.class,
        EmployeeDto.class,
        SimpleEmployeeMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class SimpleEmployeeMapperTest {

    @Test
    public void testConditionalMappingAmerican() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setCountry( "US" );
        employeeDto.setName( "Ken, Chicken" );
        employeeDto.setUniqueIdNumber( "SSID-001" );

        SimpleEmployeeMapper mapper = SimpleEmployeeMapper.INSTANCE;

        Employee result = mapper.mapEmployee( employeeDto );
        assertThat( result.getName() ) .isEqualTo( employeeDto.getName() );
        assertThat( result.getSsid() ).isEqualTo( employeeDto.getUniqueIdNumber() );
        assertThat( result.getNin() ).isNull();
    }

    @Test
    public void testConditionalMappingBrit() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setCountry( "UK" );
        employeeDto.setName( "Ken, Chicken" );
        employeeDto.setUniqueIdNumber( "SSID-001" );

        SimpleEmployeeMapper mapper = SimpleEmployeeMapper.INSTANCE;

        Employee result = mapper.mapEmployee( employeeDto );
        assertThat( result.getName() ).isEqualTo( employeeDto.getName() );
        assertThat( result.getNin() ).isEqualTo( employeeDto.getUniqueIdNumber() );
        assertThat( result.getSsid() ).isNull();
    }

    @Test
    public void testConditionalMappingNone() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setCountry( "AU" );
        employeeDto.setName( "Ken, Chicken" );
        employeeDto.setUniqueIdNumber( "SSID-001" );

        SimpleEmployeeMapper mapper = SimpleEmployeeMapper.INSTANCE;

        Employee result = mapper.mapEmployee( employeeDto );
        assertThat( result.getName() ).isEqualTo( employeeDto.getName() );
        assertThat( result.getNin() ).isNull();
        assertThat( result.getSsid() ).isNull();
    }
}
