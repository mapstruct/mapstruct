/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.iterable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    EmployeeDto.class,
    IterableElementConditionMapper.class,
})
public class IterableElementConditionTest {

    @ProcessorTest
    public void conditionalMethodListToList() {

        List<Employee> result = IterableElementConditionMapper.INSTANCE.mapListToList( setupList() );

        assertThatOnlyFilteredValuesMapped( result );
    }

    @ProcessorTest
    public void conditionalMethodArrayToList() {

        List<Employee> result = IterableElementConditionMapper.INSTANCE.mapArrayToList( setupArray() );

        assertThatOnlyFilteredValuesMapped( result );
    }

//    @ProcessorTest
//    public void conditionalMethodListToArray() {
//
//        Employee[] result = IterableElementConditionMapper.INSTANCE.mapListToArray( setupList() );
//
//        assertThatOnlyFilteredValuesMapped( result );
//    }
//
//    @ProcessorTest
//    public void conditionalMethodArrayToArray() {
//
//        Employee[] result = IterableElementConditionMapper.INSTANCE.mapArrayToArray( setupArray() );
//
//        assertThatOnlyFilteredValuesMapped( result );
//    }

    private static void assertThatOnlyFilteredValuesMapped(List<Employee> result) {
        assertThat( result )
            .singleElement()
            .satisfies(
                d -> assertThat( d.getName() ).isEqualTo( "Tester" )
            );
    }

    private static void assertThatOnlyFilteredValuesMapped(Employee[] result) {
        assertThatOnlyFilteredValuesMapped(
            Arrays.stream( result ).collect( Collectors.toList() )
        );
    }

    private static ArrayList<EmployeeDto> setupList() {

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

        return employees;
    }

    private static EmployeeDto[] setupArray() {
        return setupList().toArray( new EmployeeDto[0] );
    }

}
