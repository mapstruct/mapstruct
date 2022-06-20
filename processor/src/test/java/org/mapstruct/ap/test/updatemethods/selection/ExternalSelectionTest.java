/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.selection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.updatemethods.BossDto;
import org.mapstruct.ap.test.updatemethods.BossEntity;
import org.mapstruct.ap.test.updatemethods.CompanyDto;
import org.mapstruct.ap.test.updatemethods.CompanyEntity;
import org.mapstruct.ap.test.updatemethods.ConstructableDepartmentEntity;
import org.mapstruct.ap.test.updatemethods.DepartmentDto;
import org.mapstruct.ap.test.updatemethods.DepartmentEntity;
import org.mapstruct.ap.test.updatemethods.DepartmentEntityFactory;
import org.mapstruct.ap.test.updatemethods.EmployeeDto;
import org.mapstruct.ap.test.updatemethods.EmployeeEntity;
import org.mapstruct.ap.test.updatemethods.SecretaryDto;
import org.mapstruct.ap.test.updatemethods.SecretaryEntity;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey("160")
@WithClasses({
    OrganizationMapper1.class,
    ExternalMapper.class,
    ExternalHandWrittenMapper.class,
    CompanyDto.class,
    CompanyEntity.class,
    DepartmentMapper.class,
    DepartmentDto.class,
    DepartmentEntityFactory.class,
    DepartmentEntity.class,
    EmployeeDto.class,
    EmployeeEntity.class,
    SecretaryDto.class,
    SecretaryEntity.class,
    BossDto.class,
    BossEntity.class,
    ConstructableDepartmentEntity.class
})
public class ExternalSelectionTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        OrganizationMapper1.class,
        ExternalMapper.class,
        DepartmentMapper.class
    );

    @ProcessorTest
    @WithClasses({
        OrganizationMapper1.class
    })
    public void shouldSelectGeneratedExternalMapper() {
        generatedSource.addComparisonToFixtureFor( OrganizationMapper1.class );

        CompanyEntity entity = new CompanyEntity();
        CompanyDto dto = new CompanyDto();
        OrganizationMapper1.INSTANCE.toCompanyEntity( dto, entity );
    }

    @ProcessorTest
    @WithClasses({
        OrganizationMapper3.class
    })
    @IssueKey("604")
    public void shouldSelectGeneratedExternalMapperWithImportForPropertyType() {
        generatedSource.addComparisonToFixtureFor( OrganizationMapper3.class );

        BossEntity entity = new BossEntity();
        BossDto dto = new BossDto();
        OrganizationMapper3.INSTANCE.toBossEntity( dto, entity );
    }

    @ProcessorTest
    @WithClasses({
        OrganizationMapper2.class
    })
    public void shouldSelectGeneratedHandWrittenExternalMapper() {
        generatedSource.addComparisonToFixtureFor( OrganizationMapper2.class );

        CompanyEntity entity = new CompanyEntity();
        CompanyDto dto = new CompanyDto();
        OrganizationMapper2.INSTANCE.toCompanyEntity( dto, entity );
    }

    @ProcessorTest
    @IssueKey( "487" )
    public void shouldSelectGeneratedExternalMapperForIterablesAndMaps() {

        DepartmentDto departmentDto = new DepartmentDto();
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName( "Sarah" );
        SecretaryDto secretaryDto = new SecretaryDto();
        secretaryDto.setName( "Jim" );
        departmentDto.setEmployees( Arrays.asList( employeeDto ) );
        Map<SecretaryDto, EmployeeDto> secretaryToEmployee = new HashMap<>();
        secretaryToEmployee.put( secretaryDto, employeeDto );
        departmentDto.setSecretaryToEmployee( secretaryToEmployee );

        DepartmentEntity departmentEntity = new DepartmentEntity( 5 );

        DepartmentMapper.INSTANCE.toDepartmentEntity( departmentDto, departmentEntity );

        assertThat( departmentEntity ).isNotNull();
        assertThat( departmentEntity.getEmployees() ).isNotEmpty();
        assertThat( departmentEntity.getEmployees().get( 0 ).getName() ).isEqualTo( "Sarah" );
        assertThat( departmentEntity.getSecretaryToEmployee() ).isNotEmpty();
        Map.Entry<SecretaryEntity, EmployeeEntity> firstEntry =
            departmentEntity.getSecretaryToEmployee().entrySet().iterator().next();
        assertThat( firstEntry.getKey().getName() ).isEqualTo( "Jim" );
        assertThat( firstEntry.getValue().getName() ).isEqualTo( "Sarah" );

    }
}
