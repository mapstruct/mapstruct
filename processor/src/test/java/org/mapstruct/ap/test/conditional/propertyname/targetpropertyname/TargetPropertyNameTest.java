/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.propertyname.targetpropertyname;

import java.util.Collections;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.conditional.propertyname.Address;
import org.mapstruct.ap.test.conditional.propertyname.AddressDto;
import org.mapstruct.ap.test.conditional.propertyname.DomainModel;
import org.mapstruct.ap.test.conditional.propertyname.Employee;
import org.mapstruct.ap.test.conditional.propertyname.EmployeeDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 * @author Nikola Ivačič
 */
@IssueKey("2051")
@WithClasses({
    Address.class,
    AddressDto.class,
    Employee.class,
    EmployeeDto.class,
    DomainModel.class
})
public class TargetPropertyNameTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        ConditionalMethodInMapperWithTargetPropertyName.class
    })
    public void conditionalMethodInMapperWithTargetPropertyName() {
        ConditionalMethodInMapperWithTargetPropertyName mapper
            = ConditionalMethodInMapperWithTargetPropertyName.INSTANCE;

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName( "  " );
        employeeDto.setLastName( "Testirovich" );
        employeeDto.setOriginCountry( "US" );
        employeeDto.setOriginAddresses(
            Collections.singletonList( new AddressDto( "Testing St. 6" ) )
        );

        Employee employee = mapper.map( employeeDto );
        assertThat( employee.getLastName() ).isNull();
        assertThat( employee.getFirstName() ).isNull();
        assertThat( employee.getCountry() ).isEqualTo( "US" );
        assertThat( employee.getAddresses() )
            .extracting( Address::getStreet )
            .containsExactly( "Testing St. 6" );
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodForCollectionMapperWithTargetPropertyName.class
    })
    public void conditionalMethodForCollectionMapperWithTargetPropertyName() {
        ConditionalMethodForCollectionMapperWithTargetPropertyName mapper
            = ConditionalMethodForCollectionMapperWithTargetPropertyName.INSTANCE;

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName( "  " );
        employeeDto.setLastName( "Testirovich" );
        employeeDto.setOriginCountry( "US" );
        employeeDto.setOriginAddresses(
            Collections.singletonList( new AddressDto( "Testing St. 6" ) )
        );

        Employee employee = mapper.map( employeeDto );
        assertThat( employee.getLastName() ).isNull();
        assertThat( employee.getFirstName() ).isNull();
        assertThat( employee.getCountry() ).isEqualTo( "US" );
        assertThat( employee.getAddresses() ).isNull();
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodInUsesMapperWithTargetPropertyName.class
    })
    public void conditionalMethodInUsesMapperWithTargetPropertyName() {
        ConditionalMethodInUsesMapperWithTargetPropertyName mapper
            = ConditionalMethodInUsesMapperWithTargetPropertyName.INSTANCE;

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName( "  " );
        employeeDto.setLastName( "Testirovich" );
        employeeDto.setOriginCountry( "US" );
        employeeDto.setOriginAddresses(
            Collections.singletonList( new AddressDto( "Testing St. 6" ) )
        );

        Employee employee = mapper.map( employeeDto );
        assertThat( employee.getLastName() ).isNull();
        assertThat( employee.getFirstName() ).isNull();
        assertThat( employee.getCountry() ).isEqualTo( "US" );
        assertThat( employee.getAddresses() )
            .extracting( Address::getStreet )
            .containsExactly( "Testing St. 6" );
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodInMapperWithAllOptions.class
    })
    public void conditionalMethodInMapperWithAllOptions() {
        ConditionalMethodInMapperWithAllOptions mapper
            = ConditionalMethodInMapperWithAllOptions.INSTANCE;

        ConditionalMethodInMapperWithAllOptions.PresenceUtils utils =
            new ConditionalMethodInMapperWithAllOptions.PresenceUtils();

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName( "  " );
        employeeDto.setLastName( "Testirovich" );
        employeeDto.setOriginCountry( "US" );
        employeeDto.setOriginAddresses(
            Collections.singletonList( new AddressDto( "Testing St. 6" ) )
        );

        Employee employee = new Employee();
        mapper.map( employeeDto, employee, utils );
        assertThat( employee.getLastName() ).isNull();
        assertThat( employee.getFirstName() ).isNull();
        assertThat( employee.getCountry() ).isEqualTo( "US" );
        assertThat( employee.getAddresses() )
            .extracting( Address::getStreet )
            .containsExactly( "Testing St. 6" );
        assertThat( utils.visitedSourceNames )
            .containsExactlyInAnyOrder( "firstName", "lastName", "title", "originCountry" );
        assertThat( utils.visitedTargetNames )
            .containsExactlyInAnyOrder( "firstName", "lastName", "title", "country" );
        assertThat( utils.visitedSources ).containsExactly( "EmployeeDto" );
        assertThat( utils.visitedTargets ).containsExactly( "Employee" );
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodInMapperWithAllExceptTarget.class
    })
    public void conditionalMethodInMapperWithAllExceptTarget() {
        ConditionalMethodInMapperWithAllExceptTarget mapper
            = ConditionalMethodInMapperWithAllExceptTarget.INSTANCE;

        ConditionalMethodInMapperWithAllExceptTarget.PresenceUtils utils =
            new ConditionalMethodInMapperWithAllExceptTarget.PresenceUtils();

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName( "  " );
        employeeDto.setLastName( "Testirovich" );
        employeeDto.setOriginCountry( "US" );
        employeeDto.setOriginAddresses(
            Collections.singletonList( new AddressDto( "Testing St. 6" ) )
        );

        Employee employee = mapper.map( employeeDto, utils );
        assertThat( employee.getLastName() ).isEqualTo( "Testirovich" );
        assertThat( employee.getFirstName() ).isEqualTo( "  " );
        assertThat( employee.getCountry() ).isEqualTo( "US" );
        assertThat( employee.getAddresses() )
            .extracting( Address::getStreet )
            .containsExactly( "Testing St. 6" );
        assertThat( utils.visited )
            .containsExactlyInAnyOrder( "firstName", "lastName", "title", "country", "street" );
        assertThat( utils.visitedSources ).containsExactlyInAnyOrder( "EmployeeDto", "AddressDto" );
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodWithTargetPropertyNameInContextMapper.class
    })
    public void conditionalMethodWithTargetPropertyNameInUsesContextMapper() {
        ConditionalMethodWithTargetPropertyNameInContextMapper mapper
            = ConditionalMethodWithTargetPropertyNameInContextMapper.INSTANCE;

        ConditionalMethodWithTargetPropertyNameInContextMapper.PresenceUtils utils =
            new ConditionalMethodWithTargetPropertyNameInContextMapper.PresenceUtils();

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setLastName( "  " );
        employeeDto.setOriginCountry( "US" );
        employeeDto.setOriginAddresses(
            Collections.singletonList( new AddressDto( "Testing St. 6" ) )
        );

        Employee employee = mapper.map( employeeDto, utils );
        assertThat( employee.getLastName() ).isNull();
        assertThat( employee.getCountry() ).isEqualTo( "US" );
        assertThat( employee.getAddresses() )
            .extracting( Address::getStreet )
            .containsExactly( "Testing St. 6" );
        assertThat( utils.visited )
            .containsExactlyInAnyOrder( "firstName", "lastName", "title", "country", "street" );

        ConditionalMethodWithTargetPropertyNameInContextMapper.PresenceUtilsAllProps allPropsUtils =
            new ConditionalMethodWithTargetPropertyNameInContextMapper.PresenceUtilsAllProps();

        employeeDto = new EmployeeDto();
        employeeDto.setLastName( "Tester" );
        employeeDto.setOriginCountry( "US" );
        employeeDto.setOriginAddresses(
            Collections.singletonList( new AddressDto( "Testing St. 6" ) )
        );

        employee = mapper.map( employeeDto, allPropsUtils );
        assertThat( employee.getLastName() ).isEqualTo( "Tester" );
        assertThat( employee.getCountry() ).isEqualTo( "US" );
        assertThat( employee.getAddresses() )
            .extracting( Address::getStreet )
            .containsExactly( "Testing St. 6" );
        assertThat( allPropsUtils.visited )
            .containsExactlyInAnyOrder(
                "firstName",
                "lastName",
                "title",
                "country",
                "active",
                "age",
                "boss",
                "primaryAddress",
                "addresses",
                "street"
            );

        ConditionalMethodWithTargetPropertyNameInContextMapper.PresenceUtilsAllPropsWithSource allPropsUtilsWithSource =
            new ConditionalMethodWithTargetPropertyNameInContextMapper.PresenceUtilsAllPropsWithSource();

        EmployeeDto bossEmployeeDto = new EmployeeDto();
        bossEmployeeDto.setLastName( "Boss Tester" );
        bossEmployeeDto.setOriginCountry( "US" );
        bossEmployeeDto.setOriginAddresses( Collections.singletonList( new AddressDto(
            "Testing St. 10" ) ) );

        employeeDto = new EmployeeDto();
        employeeDto.setLastName( "Tester" );
        employeeDto.setOriginCountry( "US" );
        employeeDto.setBoss( bossEmployeeDto );
        employeeDto.setOriginAddresses(
            Collections.singletonList( new AddressDto( "Testing St. 6" ) )
        );

        employee = mapper.map( employeeDto, allPropsUtilsWithSource );
        assertThat( employee.getLastName() ).isEqualTo( "Tester" );
        assertThat( employee.getCountry() ).isEqualTo( "US" );
        assertThat( employee.getAddresses() ).isNotEmpty();
        assertThat( employee.getAddresses().get( 0 ).getStreet() ).isEqualTo( "Testing St. 6" );
        assertThat( employee.getBoss() ).isNotNull();
        assertThat( employee.getBoss().getCountry() ).isEqualTo( "US" );
        assertThat( employee.getBoss().getLastName() ).isEqualTo( "Boss Tester" );
        assertThat( employee.getBoss().getAddresses() )
            .extracting( Address::getStreet )
            .containsExactly( "Testing St. 10" );
        assertThat( allPropsUtilsWithSource.visited )
            .containsExactly(
                "country",
                "addresses",
                "addresses.street",
                "firstName",
                "lastName",
                "title",
                "active",
                "age",
                "boss",
                "boss.country",
                "boss.addresses",
                "boss.addresses.street",
                "boss.firstName",
                "boss.lastName",
                "boss.title",
                "boss.active",
                "boss.age",
                "boss.boss",
                "boss.primaryAddress",
                "primaryAddress"
            );
    }

    @IssueKey("2863")
    @ProcessorTest
    @WithClasses({
        ErroneousNonStringTargetPropertyNameParameter.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousNonStringTargetPropertyNameParameter.class,
                line = 23,
                message = "@TargetPropertyName can only by applied to a String parameter."
            )
        }
    )
    public void nonStringTargetPropertyNameParameter() {

    }
}
