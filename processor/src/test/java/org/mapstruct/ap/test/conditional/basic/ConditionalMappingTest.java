/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2051")
@WithClasses({
    BasicEmployee.class,
    BasicEmployeeDto.class,
    TargetPropertyNameModel.class
})
public class ConditionalMappingTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        ConditionalMethodInMapper.class
    })
    public void conditionalMethodInMapper() {
        generatedSource.addComparisonToFixtureFor( ConditionalMethodInMapper.class );
        ConditionalMethodInMapper mapper = ConditionalMethodInMapper.INSTANCE;

        BasicEmployee employee = mapper.map( new BasicEmployeeDto( "Tester" ) );
        assertThat( employee.getName() ).isEqualTo( "Tester" );

        employee = mapper.map( new BasicEmployeeDto( "" ) );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new BasicEmployeeDto( "    " ) );
        assertThat( employee.getName() ).isNull();
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodAndBeanPresenceCheckMapper.class
    })
    public void conditionalMethodAndBeanPresenceCheckMapper() {
        ConditionalMethodAndBeanPresenceCheckMapper mapper = ConditionalMethodAndBeanPresenceCheckMapper.INSTANCE;

        BasicEmployee employee = mapper.map( new ConditionalMethodAndBeanPresenceCheckMapper.EmployeeDto( "Tester" ) );
        assertThat( employee.getName() ).isEqualTo( "Tester" );

        employee = mapper.map( new ConditionalMethodAndBeanPresenceCheckMapper.EmployeeDto( "" ) );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new ConditionalMethodAndBeanPresenceCheckMapper.EmployeeDto( "    " ) );
        assertThat( employee.getName() ).isNull();
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodInUsesMapper.class
    })
    public void conditionalMethodInUsesMapper() {
        ConditionalMethodInUsesMapper mapper = ConditionalMethodInUsesMapper.INSTANCE;

        BasicEmployee employee = mapper.map( new BasicEmployeeDto( "Tester" ) );
        assertThat( employee.getName() ).isEqualTo( "Tester" );

        employee = mapper.map( new BasicEmployeeDto( "" ) );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new BasicEmployeeDto( "    " ) );
        assertThat( employee.getName() ).isNull();
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodInUsesStaticMapper.class
    })
    public void conditionalMethodInUsesStaticMapper() {
        ConditionalMethodInUsesStaticMapper mapper = ConditionalMethodInUsesStaticMapper.INSTANCE;

        BasicEmployee employee = mapper.map( new BasicEmployeeDto( "Tester" ) );
        assertThat( employee.getName() ).isEqualTo( "Tester" );

        employee = mapper.map( new BasicEmployeeDto( "" ) );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new BasicEmployeeDto( "    " ) );
        assertThat( employee.getName() ).isNull();
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodInContextMapper.class
    })
    public void conditionalMethodInUsesContextMapper() {
        ConditionalMethodInContextMapper mapper = ConditionalMethodInContextMapper.INSTANCE;

        ConditionalMethodInContextMapper.PresenceUtils utils = new ConditionalMethodInContextMapper.PresenceUtils();
        BasicEmployee employee = mapper.map( new BasicEmployeeDto( "Tester" ), utils );
        assertThat( employee.getName() ).isEqualTo( "Tester" );

        employee = mapper.map( new BasicEmployeeDto( "" ), utils );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new BasicEmployeeDto( "    " ), utils );
        assertThat( employee.getName() ).isNull();
    }

    @ProcessorTest
    @WithClasses({
            ConditionalMethodInMapperWithTargetPropertyName.class
    })
    public void conditionalMethodInMapperWithTargetPropertyName() {
        ConditionalMethodInMapperWithTargetPropertyName mapper
                = ConditionalMethodInMapperWithTargetPropertyName.INSTANCE;

        TargetPropertyNameModel.EmployeeDto employeeDto = new TargetPropertyNameModel.EmployeeDto();
        employeeDto.setFirstName( "  " );
        employeeDto.setLastName( "Testirovich" );
        employeeDto.setCountry( "US" );
        employeeDto.setAddresses( List.of( new TargetPropertyNameModel.AddressDto( "Testing St. 6" ) ) );

        TargetPropertyNameModel.Employee employee = mapper.map( employeeDto );
        assertThat( employee.getLastName() ).isNull();
        assertThat( employee.getFirstName() ).isNull();
        assertThat( employee.getCountry() ).isEqualTo( "US" );
        assertThat( employee.getAddresses() ).isNotEmpty();
        assertThat( employee.getAddresses().get( 0 ).getStreet() ).isEqualTo( "Testing St. 6" );
    }

    @ProcessorTest
    @WithClasses({
            ConditionalMethodForCollectionMapperWithTargetPropertyName.class
    })
    public void conditionalMethodForCollectionMapperWithTargetPropertyName() {
        ConditionalMethodForCollectionMapperWithTargetPropertyName mapper
                = ConditionalMethodForCollectionMapperWithTargetPropertyName.INSTANCE;

        TargetPropertyNameModel.EmployeeDto employeeDto = new TargetPropertyNameModel.EmployeeDto();
        employeeDto.setFirstName( "  " );
        employeeDto.setLastName( "Testirovich" );
        employeeDto.setCountry( "US" );
        employeeDto.setAddresses( List.of( new TargetPropertyNameModel.AddressDto( "Testing St. 6" ) ) );

        TargetPropertyNameModel.Employee employee = mapper.map( employeeDto );
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

        TargetPropertyNameModel.EmployeeDto employeeDto = new TargetPropertyNameModel.EmployeeDto();
        employeeDto.setFirstName( "  " );
        employeeDto.setLastName( "Testirovich" );
        employeeDto.setCountry( "US" );
        employeeDto.setAddresses( List.of( new TargetPropertyNameModel.AddressDto( "Testing St. 6" ) ) );

        TargetPropertyNameModel.Employee employee = mapper.map( employeeDto );
        assertThat( employee.getLastName() ).isNull();
        assertThat( employee.getFirstName() ).isNull();
        assertThat( employee.getCountry() ).isEqualTo( "US" );
        assertThat( employee.getAddresses() ).isNotEmpty();
        assertThat( employee.getAddresses().get( 0 ).getStreet() ).isEqualTo( "Testing St. 6" );
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

        TargetPropertyNameModel.EmployeeDto employeeDto = new TargetPropertyNameModel.EmployeeDto();
        employeeDto.setFirstName( "  " );
        employeeDto.setLastName( "Testirovich" );
        employeeDto.setCountry( "US" );
        employeeDto.setAddresses( List.of( new TargetPropertyNameModel.AddressDto( "Testing St. 6" ) ) );

        TargetPropertyNameModel.Employee employee = new TargetPropertyNameModel.Employee();
        mapper.map( employeeDto, employee, utils );
        assertThat( employee.getLastName() ).isNull();
        assertThat( employee.getFirstName() ).isNull();
        assertThat( employee.getCountry() ).isEqualTo( "US" );
        assertThat( employee.getAddresses() ).isNotEmpty();
        assertThat( employee.getAddresses().get( 0 ).getStreet() ).isEqualTo( "Testing St. 6" );
        assertThat( Set.of(
                "firstName",
                "lastName",
                "title",
                "country" )
        ).isEqualTo( utils.visited );
        assertThat( Set.of( "EmployeeDto" ) ).isEqualTo( utils.visitedSources );
        assertThat( Set.of( "Employee" ) ).isEqualTo( utils.visitedTargets );
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

        TargetPropertyNameModel.EmployeeDto employeeDto = new TargetPropertyNameModel.EmployeeDto();
        employeeDto.setFirstName( "  " );
        employeeDto.setLastName( "Testirovich" );
        employeeDto.setCountry( "US" );
        employeeDto.setAddresses( List.of( new TargetPropertyNameModel.AddressDto( "Testing St. 6" ) ) );

        TargetPropertyNameModel.Employee employee = mapper.map( employeeDto, utils );
        assertThat( employee.getLastName() ).isEqualTo( "Testirovich" );
        assertThat( employee.getFirstName() ).isEqualTo( "  " );
        assertThat( employee.getCountry() ).isEqualTo( "US" );
        assertThat( employee.getAddresses() ).isNotEmpty();
        assertThat( employee.getAddresses().get( 0 ).getStreet() ).isEqualTo( "Testing St. 6" );
        assertThat( Set.of(
                "firstName",
                "lastName",
                "title",
                "country",
                "street" )
        ).isEqualTo( utils.visited );
        assertThat( Set.of( "EmployeeDto", "AddressDto" ) )
                .isEqualTo( utils.visitedSources );
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

      TargetPropertyNameModel.EmployeeDto employeeDto = new TargetPropertyNameModel.EmployeeDto();
      employeeDto.setLastName( "  " );
      employeeDto.setCountry( "US" );
      employeeDto.setAddresses( List.of( new TargetPropertyNameModel.AddressDto( "Testing St. 6" ) ) );

      TargetPropertyNameModel.Employee employee = mapper.map( employeeDto, utils );
      assertThat( employee.getLastName() ).isNull();
      assertThat( employee.getCountry() ).isEqualTo( "US" );
      assertThat( employee.getAddresses() ).isNotEmpty();
      assertThat( employee.getAddresses().get( 0 ).getStreet() ).isEqualTo( "Testing St. 6" );
      assertThat( Set.of(
              "firstName",
              "lastName",
              "title",
              "country",
              "street" )
      ).isEqualTo( utils.visited );


      ConditionalMethodWithTargetPropertyNameInContextMapper.PresenceUtilsAllProps allPropsUtils =
        new ConditionalMethodWithTargetPropertyNameInContextMapper.PresenceUtilsAllProps();

      employeeDto = new TargetPropertyNameModel.EmployeeDto();
      employeeDto.setLastName( "Tester" );
      employeeDto.setCountry( "US" );
      employeeDto.setAddresses( List.of( new TargetPropertyNameModel.AddressDto( "Testing St. 6" ) ) );

      employee = mapper.map( employeeDto, allPropsUtils );
      assertThat( employee.getLastName() ).isEqualTo( "Tester" );
      assertThat( employee.getCountry() ).isEqualTo( "US" );
      assertThat( employee.getAddresses() ).isNotEmpty();
      assertThat( employee.getAddresses().get( 0 ).getStreet() ).isEqualTo( "Testing St. 6" );
      assertThat( Set.of(
              "firstName",
              "lastName",
              "title",
              "country",
              "active",
              "age",
              "boss",
              "primaryAddress",
              "addresses",
              "street" )
      ).isEqualTo( allPropsUtils.visited );


      ConditionalMethodWithTargetPropertyNameInContextMapper.PresenceUtilsAllPropsWithSource allPropsUtilsWithSource =
        new ConditionalMethodWithTargetPropertyNameInContextMapper.PresenceUtilsAllPropsWithSource();


      TargetPropertyNameModel.EmployeeDto bossEmployeeDto = new TargetPropertyNameModel.EmployeeDto();
      bossEmployeeDto.setLastName( "Boss Tester" );
      bossEmployeeDto.setCountry( "US" );
      bossEmployeeDto.setAddresses( List.of( new TargetPropertyNameModel.AddressDto( "Testing St. 10" ) ) );

      employeeDto = new TargetPropertyNameModel.EmployeeDto();
      employeeDto.setLastName( "Tester" );
      employeeDto.setCountry( "US" );
      employeeDto.setBoss( bossEmployeeDto );
      employeeDto.setAddresses( List.of( new TargetPropertyNameModel.AddressDto( "Testing St. 6" ) ) );

      employee = mapper.map( employeeDto, allPropsUtilsWithSource );
      assertThat( employee.getLastName() ).isEqualTo( "Tester" );
      assertThat( employee.getCountry() ).isEqualTo( "US" );
      assertThat( employee.getAddresses() ).isNotEmpty();
      assertThat( employee.getAddresses().get( 0 ).getStreet() ).isEqualTo( "Testing St. 6" );
      assertThat( employee.getBoss() ).isNotNull();
      assertThat( employee.getBoss().getCountry() ).isEqualTo( "US" );
      assertThat( employee.getBoss().getLastName() ).isEqualTo( "Boss Tester" );
      assertThat( employee.getBoss().getAddresses() ).isNotEmpty();
      assertThat( employee.getBoss().getAddresses().get( 0 ).getStreet() ).isEqualTo( "Testing St. 10" );
      assertThat( List.of(
              "firstName",
              "lastName",
              "title",
              "country",
              "active",
              "age",
              "boss",
              "boss.firstName",
              "boss.lastName",
              "boss.title",
              "boss.country",
              "boss.active",
              "boss.age",
              "boss.boss",
              "boss.primaryAddress",
              "boss.addresses",
              "boss.addresses.street",
              "primaryAddress",
              "addresses",
              "addresses.street" )
      ).isEqualTo( allPropsUtilsWithSource.visited );
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodWithSourceParameterMapper.class
    })
    public void conditionalMethodWithSourceParameter() {
        ConditionalMethodWithSourceParameterMapper mapper = ConditionalMethodWithSourceParameterMapper.INSTANCE;

        BasicEmployee employee = mapper.map( new BasicEmployeeDto( "Tester" ) );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new BasicEmployeeDto( "Tester", "map" ) );
        assertThat( employee.getName() ).isEqualTo( "Tester" );
    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodWithSourceParameterAndValueMapper.class
    })
    public void conditionalMethodWithSourceParameterAndValue() {
        generatedSource.addComparisonToFixtureFor( ConditionalMethodWithSourceParameterAndValueMapper.class );
        ConditionalMethodWithSourceParameterAndValueMapper mapper =
            ConditionalMethodWithSourceParameterAndValueMapper.INSTANCE;

        BasicEmployee employee = mapper.map( new BasicEmployeeDto( "    ", "empty" ) );
        assertThat( employee.getName() ).isEqualTo( "    " );

        employee = mapper.map( new BasicEmployeeDto( "    ", "blank" ) );
        assertThat( employee.getName() ).isNull();

        employee = mapper.map( new BasicEmployeeDto( "Tester", "blank" ) );
        assertThat( employee.getName() ).isEqualTo( "Tester" );
    }

    @ProcessorTest
    @WithClasses({
        ErroneousAmbiguousConditionalMethodMapper.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousAmbiguousConditionalMethodMapper.class,
                line = 17,
                message = "Ambiguous presence check methods found for checking String: " +
                    "boolean isNotBlank(String value), " +
                    "boolean isNotEmpty(String value). " +
                    "See https://mapstruct.org/faq/#ambiguous for more info."
            )
        }
    )
    public void ambiguousConditionalMethod() {

    }

    @ProcessorTest
    @WithClasses({
        ConditionalMethodForCollectionMapper.class
    })
    public void conditionalMethodForCollection() {
        ConditionalMethodForCollectionMapper mapper = ConditionalMethodForCollectionMapper.INSTANCE;

        ConditionalMethodForCollectionMapper.Author author = new ConditionalMethodForCollectionMapper.Author();
        ConditionalMethodForCollectionMapper.AuthorDto dto = mapper.map( author );

        assertThat( dto.getBooks() ).isNull();

        author.setBooks( Collections.emptyList() );
        dto = mapper.map( author );

        assertThat( dto.getBooks() ).isNull();

        author.setBooks( Arrays.asList(
            new ConditionalMethodForCollectionMapper.Book( "Test" ),
            new ConditionalMethodForCollectionMapper.Book( "Test Vol. 2" )
        ) );
        dto = mapper.map( author );

        assertThat( dto.getBooks() )
            .extracting( ConditionalMethodForCollectionMapper.BookDto::getName )
            .containsExactly( "Test", "Test Vol. 2" );
    }

    @ProcessorTest
    @WithClasses({
        OptionalLikeConditionalMapper.class
    })
    @IssueKey("2084")
    public void optionalLikeConditional() {
        OptionalLikeConditionalMapper mapper = OptionalLikeConditionalMapper.INSTANCE;

        OptionalLikeConditionalMapper.Target target = mapper.map( new OptionalLikeConditionalMapper.Source(
            OptionalLikeConditionalMapper.Nullable.ofNullable( "test" ) ) );

        assertThat( target.getValue() ).isEqualTo( "test" );

        target = mapper.map(
            new OptionalLikeConditionalMapper.Source( OptionalLikeConditionalMapper.Nullable.undefined() )
        );

        assertThat( target.getValue() ).isEqualTo( "initial" );

    }

    @ProcessorTest
    @WithClasses( {
        ConditionalMethodWithMappingTargetInUpdateMapper.class
    } )
    @IssueKey( "2758" )
    public void conditionalMethodWithMappingTarget() {
        ConditionalMethodWithMappingTargetInUpdateMapper mapper =
            ConditionalMethodWithMappingTargetInUpdateMapper.INSTANCE;

        BasicEmployee targetEmployee = new BasicEmployee();
        targetEmployee.setName( "CurrentName" );
        mapper.map( new BasicEmployeeDto( "ReplacementName" ), targetEmployee );

        assertThat( targetEmployee.getName() ).isEqualTo( "CurrentName" );
    }
}
