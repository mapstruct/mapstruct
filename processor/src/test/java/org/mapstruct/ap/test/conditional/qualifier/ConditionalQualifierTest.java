/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.qualifier;

import org.mapstruct.ap.test.conditional.Employee;
import org.mapstruct.ap.test.conditional.EmployeeDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2051")
@WithClasses({
    Employee.class,
    EmployeeDto.class
})
public class ConditionalQualifierTest {

    @ProcessorTest
    @WithClasses({
        ConditionalMethodWithSourceParameterMapper.class
    })
    public void conditionalMethodWithSourceParameter() {
        ConditionalMethodWithSourceParameterMapper mapper = ConditionalMethodWithSourceParameterMapper.INSTANCE;

        EmployeeDto dto = new EmployeeDto();
        dto.setName( "Tester" );
        dto.setUniqueIdNumber( "SSID-001" );
        dto.setCountry( null );

        Employee employee = mapper.map( dto );
        assertThat( employee.getNin() ).isNull();
        assertThat( employee.getSsid() ).isNull();

        dto.setCountry( "UK" );
        employee = mapper.map( dto );
        assertThat( employee.getNin() ).isEqualTo( "SSID-001" );
        assertThat( employee.getSsid() ).isNull();

        dto.setCountry( "US" );
        employee = mapper.map( dto );
        assertThat( employee.getNin() ).isNull();
        assertThat( employee.getSsid() ).isEqualTo( "SSID-001" );

        dto.setCountry( "CH" );
        employee = mapper.map( dto );
        assertThat( employee.getNin() ).isNull();
        assertThat( employee.getSsid() ).isNull();
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodWithClassQualifiersMapper.class
    })
    public void conditionalClassQualifiers() {
        ConditionalMethodWithClassQualifiersMapper mapper = ConditionalMethodWithClassQualifiersMapper.INSTANCE;

        EmployeeDto dto = new EmployeeDto();
        dto.setName( "Tester" );
        dto.setUniqueIdNumber( "SSID-001" );
        dto.setCountry( null );

        Employee employee = mapper.map( dto );
        assertThat( employee.getNin() ).isNull();
        assertThat( employee.getSsid() ).isNull();

        dto.setCountry( "UK" );
        employee = mapper.map( dto );
        assertThat( employee.getNin() ).isEqualTo( "SSID-001" );
        assertThat( employee.getSsid() ).isNull();

        dto.setCountry( "US" );
        employee = mapper.map( dto );
        assertThat( employee.getNin() ).isNull();
        assertThat( employee.getSsid() ).isEqualTo( "SSID-001" );

        dto.setCountry( "CH" );
        employee = mapper.map( dto );
        assertThat( employee.getNin() ).isNull();
        assertThat( employee.getSsid() ).isNull();
    }
}
