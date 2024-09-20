/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.iterable;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.test.conditional.Employee;
import org.mapstruct.ap.test.conditional.EmployeeDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oliver Erhart
 */
@IssueKey("1610")
@WithClasses({
    Employee.class,
    EmployeeDto.class
})
public class IterableElementConditionTest {

    @ProcessorTest
    @WithClasses({
        IterableElementConditionMapper.class
    })
    public void conditionalMethodWithSourceParameter() {
        IterableElementConditionMapper mapper = IterableElementConditionMapper.INSTANCE;

        EmployeeDto dtoWithoutCountry = new EmployeeDto();
        dtoWithoutCountry.setName( "Tester" );
        dtoWithoutCountry.setUniqueIdNumber( "SSID-001" );
        dtoWithoutCountry.setCountry( null );

        EmployeeDto dtoWithCountry = new EmployeeDto();
        dtoWithCountry.setName( "Tester" );
        dtoWithCountry.setUniqueIdNumber( "SSID-001" );
        dtoWithCountry.setCountry( "Austria" );

        ArrayList<EmployeeDto> employees = new ArrayList<>();
        employees.add( dtoWithoutCountry );
        employees.add( dtoWithCountry );

        List<Employee> result = mapper.map( employees );
        assertThat( result )
            .singleElement()
            .satisfies(
                d -> assertThat(d.getName()).isEqualTo( "Tester" )
            );

    }

}
