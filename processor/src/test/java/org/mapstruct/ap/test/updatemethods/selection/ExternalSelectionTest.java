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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.updatemethods.CompanyDto;
import org.mapstruct.ap.test.updatemethods.CompanyEntity;
import org.mapstruct.ap.test.updatemethods.DepartmentDto;
import org.mapstruct.ap.test.updatemethods.DepartmentEntity;
import org.mapstruct.ap.test.updatemethods.DepartmentEntityFactory;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey("160")
@RunWith(AnnotationProcessorTestRunner.class)
public class ExternalSelectionTest {

    @Test
    @WithClasses({
        OrganizationMapper1.class,
        ExternalMapper.class,
        CompanyDto.class,
        CompanyEntity.class,
        DepartmentDto.class,
        DepartmentEntityFactory.class,
        DepartmentEntity.class
    })
    public void shouldSelectGeneratedExternalMapper() {

        CompanyEntity entity = new CompanyEntity();
        CompanyDto dto = new CompanyDto();
        OrganizationMapper1.INSTANCE.toCompanyEntity( dto, entity );
    }

    @Test
    @WithClasses({
        OrganizationMapper2.class,
        ExternalHandWrittenMapper.class,
        CompanyDto.class,
        CompanyEntity.class,
        DepartmentDto.class,
        DepartmentEntityFactory.class,
        DepartmentEntity.class
    })
    public void shouldSelectGeneratedHandWrittenExternalMapper() {

        CompanyEntity entity = new CompanyEntity();
        CompanyDto dto = new CompanyDto();
        OrganizationMapper2.INSTANCE.toCompanyEntity( dto, entity );
    }

}
