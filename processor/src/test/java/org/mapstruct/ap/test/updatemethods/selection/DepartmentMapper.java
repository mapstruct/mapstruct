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

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ap.test.updatemethods.DepartmentDto;
import org.mapstruct.ap.test.updatemethods.DepartmentEntity;
import org.mapstruct.ap.test.updatemethods.EmployeeDto;
import org.mapstruct.ap.test.updatemethods.EmployeeEntity;
import org.mapstruct.ap.test.updatemethods.SecretaryDto;
import org.mapstruct.ap.test.updatemethods.SecretaryEntity;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( uses = ExternalHandWrittenMapper.class )
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper( DepartmentMapper.class );

    void toDepartmentEntity(DepartmentDto dto, @MappingTarget DepartmentEntity entity);

    EmployeeEntity toEmployeeEntity(EmployeeDto dto);

    SecretaryEntity toSecretaryEntity(SecretaryDto dto);

}
