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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( uses = DepartmentEntityFactory.class )
public interface ErroneousOrganizationMapper1 {

    ErroneousOrganizationMapper1 INSTANCE = Mappers.getMapper( ErroneousOrganizationMapper1.class );

    @Mapping(target = "type", constant = "commercial")
    void toOrganizationEntity(OrganizationDto dto, @MappingTarget OrganizationWithoutCompanyGetterEntity entity);

    void toCompanyEntity(CompanyDto dto, @MappingTarget CompanyEntity entity);

    @Mapping(source = "type", target = "type")
    void toName(String type, @MappingTarget OrganizationTypeEntity entity);

    @Mappings({
        @Mapping( target = "employees", ignore = true ),
        @Mapping( target = "secretaryToEmployee", ignore = true )
    })
    DepartmentEntity toDepartmentEntity(DepartmentDto dto);

}
