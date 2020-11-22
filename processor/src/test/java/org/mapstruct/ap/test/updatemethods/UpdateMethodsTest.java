/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey("160")
@RunWith(AnnotationProcessorTestRunner.class)
 @WithClasses({
    OrganizationDto.class,
    OrganizationEntity.class,
    CompanyDto.class,
    CompanyEntity.class,
    DepartmentDto.class,
    DepartmentEntity.class,
    DepartmentEntityFactory.class,
    OrganizationTypeEntity.class,
    OrganizationTypeNrEntity.class,
    EmployeeDto.class,
    EmployeeEntity.class,
    SecretaryDto.class,
    SecretaryEntity.class
})
public class UpdateMethodsTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Test
    @WithClasses( {
        OrganizationMapper.class
    } )
    public void testPreferUpdateMethod() {
        generatedSource.addComparisonToFixtureFor( OrganizationMapper.class );

        OrganizationEntity organizationEntity = new OrganizationEntity();
        CompanyEntity companyEntity = new CompanyEntity();
        organizationEntity.setCompany( companyEntity );
        companyEntity.setName( "CocaCola" );

        OrganizationDto organizationDto = new OrganizationDto();
        CompanyDto companyDto = new CompanyDto();
        organizationDto.setCompany( companyDto );
        companyDto.setName( "PepsiCo" );
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName( "finance" );
        companyDto.setDepartment( departmentDto );

        OrganizationMapper.INSTANCE.toOrganizationEntity( organizationDto, organizationEntity );

        assertThat( organizationEntity.getCompany() ).isEqualTo( companyEntity );
        assertThat( organizationEntity.getCompany().getName() ).isEqualTo( "PepsiCo" );
        assertThat( organizationEntity.getType().getType() ).isEqualTo( "commercial" );
        assertThat( organizationEntity.getTypeNr().getNumber() ).isEqualTo( 5 );
        assertThat( organizationEntity.getCompany().getDepartment().getName() ).isEqualTo( "finance" );
    }

    @Test
    @WithClasses( {
        OrganizationMapper.class
    } )
    public void testUpdateMethodClearsExistingValues() {
        generatedSource.addComparisonToFixtureFor( OrganizationMapper.class );

        OrganizationEntity organizationEntity = new OrganizationEntity();
        CompanyEntity companyEntity = new CompanyEntity();
        organizationEntity.setCompany( companyEntity );
        companyEntity.setName( "CocaCola" );
        DepartmentEntity department = new DepartmentEntity( null );
        department.setName( "recipies" );
        companyEntity.setDepartment( department );

        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setCompany( null );

        OrganizationMapper.INSTANCE.toOrganizationEntity( organizationDto, organizationEntity );

        assertThat( organizationEntity.getCompany() ).isNull();
        assertThat( organizationEntity.getType().getType() ).isEqualTo( "commercial" );
        assertThat( organizationEntity.getTypeNr().getNumber() ).isEqualTo( 5 );
    }

    @Test
    @WithClasses({
        OrganizationMapper.class
    })
    public void testPreferUpdateMethodSourceObjectNotDefined() {
        generatedSource.addComparisonToFixtureFor( OrganizationMapper.class );

        OrganizationEntity organizationEntity = new OrganizationEntity();

        OrganizationDto organizationDto = new OrganizationDto();
        CompanyDto companyDto = new CompanyDto();
        organizationDto.setCompany( companyDto );
        companyDto.setName( "PepsiCo" );
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName( "finance" );
        companyDto.setDepartment( departmentDto );

        OrganizationMapper.INSTANCE.toOrganizationEntity( organizationDto, organizationEntity );

        assertThat( organizationEntity.getCompany().getName() ).isEqualTo( "PepsiCo" );
        assertThat( organizationEntity.getType().getType() ).isEqualTo( "commercial" );
        assertThat( organizationEntity.getTypeNr().getNumber() ).isEqualTo( 5 );
        assertThat( organizationEntity.getCompany().getDepartment().getName() ).isEqualTo( "finance" );
    }

    @Test
    @WithClasses( {
        CompanyMapper.class,
        DepartmentInBetween.class
    } )
    public void testPreferUpdateMethodEncapsulatingCreateMethod() {
        generatedSource.addComparisonToFixtureFor( CompanyMapper.class );

        CompanyEntity companyEntity = new CompanyEntity();

        CompanyDto companyDto = new CompanyDto();
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName( "finance" );
        companyDto.setDepartment( departmentDto );

        CompanyMapper.INSTANCE.toCompanyEntity( companyDto, companyEntity );

        assertThat( companyEntity.getDepartment().getName() ).isEqualTo( "finance" );

    }

    @Test
    @WithClasses( {
        ErroneousOrganizationMapper1.class,
        OrganizationWithoutCompanyGetterEntity.class
    } )
   @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrganizationMapper1.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 24,
                message = "No read accessor found for property \"company\" in target type.")
        }
   )
    public void testShouldFailOnPropertyMappingNoPropertyGetter() { }

    @Test
    @WithClasses({
        ErroneousOrganizationMapper2.class,
        OrganizationWithoutTypeGetterEntity.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrganizationMapper2.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 23,
                message = "No read accessor found for property \"type\" in target type."),
            @Diagnostic(type = ErroneousOrganizationMapper2.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 35,
                message = "ErroneousOrganizationMapper2.DepartmentEntity does not have an accessible constructor.")

        })
    public void testShouldFailOnConstantMappingNoPropertyGetter() {
    }

    @Test
    @WithClasses({
        CompanyMapper1.class,
        DepartmentInBetween.class,
        UnmappableCompanyDto.class,
        UnmappableDepartmentDto.class

    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = CompanyMapper1.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 23,
                message = "Unmapped target property: \"employees\". " +
                    "Mapping from property \"UnmappableDepartmentDto department\" to \"DepartmentEntity department\".")
        }
    )
    public void testShouldNotUseTwoNestedUpdateMethods() {
        generatedSource.addComparisonToFixtureFor( CompanyMapper1.class );
    }
}
