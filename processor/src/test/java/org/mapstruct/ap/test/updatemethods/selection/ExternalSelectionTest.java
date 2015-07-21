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
package org.mapstruct.ap.test.updatemethods.selection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey("160")
@RunWith(AnnotationProcessorTestRunner.class)
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

    @Test
    @WithClasses({
        OrganizationMapper1.class
    })
    public void shouldSelectGeneratedExternalMapper() {

        CompanyEntity entity = new CompanyEntity();
        CompanyDto dto = new CompanyDto();
        OrganizationMapper1.INSTANCE.toCompanyEntity( dto, entity );
    }

    @Test
    @WithClasses({
        OrganizationMapper3.class
    })
    @IssueKey("604")
    public void shouldSelectGeneratedExternalMapperWithImportForPropertyType() {

        BossEntity entity = new BossEntity();
        BossDto dto = new BossDto();
        OrganizationMapper3.INSTANCE.toBossEntity( dto, entity );
    }

    @Test
    @WithClasses({
        OrganizationMapper2.class
    })
    public void shouldSelectGeneratedHandWrittenExternalMapper() {

        CompanyEntity entity = new CompanyEntity();
        CompanyDto dto = new CompanyDto();
        OrganizationMapper2.INSTANCE.toCompanyEntity( dto, entity );
    }


    @Test
    @IssueKey( "487" )
    public void shouldSelectGeneratedExternalMapperForIterablesAndMaps() {

        DepartmentDto departmentDto = new DepartmentDto();
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName( "Sarah" );
        SecretaryDto secretaryDto = new SecretaryDto();
        secretaryDto.setName( "Jim" );
        departmentDto.setEmployees( Arrays.asList( employeeDto ) );
        Map<SecretaryDto, EmployeeDto> secretaryToEmployee = new HashMap<SecretaryDto, EmployeeDto>();
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
