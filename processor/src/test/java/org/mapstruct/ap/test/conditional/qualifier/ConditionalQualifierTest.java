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

    @ProcessorTest
    @WithClasses({
        ConditionalMethodWithSourceToTargetMapper.class
    })
    @IssueKey("2666")
    public void conditionalQualifiersForSourceToTarget() {
        ConditionalMethodWithSourceToTargetMapper mapper = ConditionalMethodWithSourceToTargetMapper.INSTANCE;

        ConditionalMethodWithSourceToTargetMapper.OrderDTO orderDto =
            new ConditionalMethodWithSourceToTargetMapper.OrderDTO();

        ConditionalMethodWithSourceToTargetMapper.Order order = mapper.convertToOrder( orderDto );
        assertThat( order ).isNotNull();
        assertThat( order.getCustomer() ).isNull();

        orderDto = new ConditionalMethodWithSourceToTargetMapper.OrderDTO();
        orderDto.setCustomerName( "Tester" );
        order = mapper.convertToOrder( orderDto );

        assertThat( order ).isNotNull();
        assertThat( order.getCustomer() ).isNotNull();
        assertThat( order.getCustomer().getName() ).isEqualTo( "Tester" );
        assertThat( order.getCustomer().getAddress() ).isNull();

        orderDto = new ConditionalMethodWithSourceToTargetMapper.OrderDTO();
        orderDto.setLine1( "Line 1" );
        order = mapper.convertToOrder( orderDto );

        assertThat( order ).isNotNull();
        assertThat( order.getCustomer() ).isNotNull();
        assertThat( order.getCustomer().getName() ).isNull();
        assertThat( order.getCustomer().getAddress() ).isNotNull();
        assertThat( order.getCustomer().getAddress().getLine1() ).isEqualTo( "Line 1" );
        assertThat( order.getCustomer().getAddress().getLine2() ).isNull();

    }
}
