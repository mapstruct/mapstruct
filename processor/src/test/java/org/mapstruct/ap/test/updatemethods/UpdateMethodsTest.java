/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.updatemethods;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

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

    @Test
    @WithClasses( {
        OrganizationMapper.class
    } )
    public void testPreferUpdateMethod() {

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
        assertThat( organizationEntity.getTypeNr().getNumber()).isEqualTo( 5 );
        assertThat( organizationEntity.getCompany().getDepartment().getName() ).isEqualTo( "finance" );
    }

    @Test
    @WithClasses( {
        OrganizationMapper.class
    } )
    public void testUpdateMethodClearsExistingValues() {

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
        assertThat( organizationEntity.getTypeNr().getNumber()).isEqualTo( 5 );
        assertThat( organizationEntity.getCompany().getDepartment().getName() ).isEqualTo( "finance" );
    }

    @Test
    @WithClasses( {
        CompanyMapper.class,
        DepartmentInBetween.class
    } )
    public void testPreferUpdateMethodEncapsulatingCreateMethod() {

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
                line = 37,
                messageRegExp = "No read accessor found for property \"company\" in target type.")
        }
   )
    public void testShouldFailOnPropertyMappingNoPropertyGetter() { }

    @Test
    @WithClasses( {
        ErroneousOrganizationMapper2.class,
        OrganizationWithoutTypeGetterEntity.class
    } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousOrganizationMapper2.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 37,
                messageRegExp = "No read accessor found for property \"type\" in target type.")
    } )
    public void testShouldFailOnConstantMappingNoPropertyGetter() { }

    @Test
    @WithClasses( {
        ErroneousCompanyMapper1.class,
        DepartmentInBetween.class

    } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCompanyMapper1.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 36,
                messageRegExp = "Can't map property \".*DepartmentDto department\" to \".*DepartmentEntity department.")
        }
    )
    public void testShouldFailOnTwoNestedUpdateMethods() { }
}
