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

import java.util.List;
import java.util.Map;
import org.mapstruct.MappingTarget;
import org.mapstruct.ap.test.updatemethods.DepartmentDto;
import org.mapstruct.ap.test.updatemethods.DepartmentEntity;
import org.mapstruct.ap.test.updatemethods.EmployeeDto;
import org.mapstruct.ap.test.updatemethods.EmployeeEntity;
import org.mapstruct.ap.test.updatemethods.SecretaryDto;
import org.mapstruct.ap.test.updatemethods.SecretaryEntity;

/**
 *
 * @author Sjaak Derksen
 */
public class ExternalHandWrittenMapper {


    public void toDepartmentEntity(DepartmentDto dto, @MappingTarget DepartmentEntity entity) {
        if ( entity != null && dto != null ) {
            entity.setName( dto.getName() );
        }
    }

    public void toEmployeeEntityList(List<EmployeeDto> dtos, @MappingTarget List<EmployeeEntity> entities) {

        if ( entities != null && dtos != null ) {
            for ( EmployeeDto dto : dtos) {
                entities.add( DepartmentMapper.INSTANCE.toEmployeeEntity( dto ) );
            }
        }

    }

    public void toSecretaryEmployeeEntityMap(Map<SecretaryDto, EmployeeDto> dtoMap,
        @MappingTarget Map<SecretaryEntity, EmployeeEntity> entityMap) {

        if ( entityMap != null && dtoMap != null ) {
            for ( Map.Entry<SecretaryDto, EmployeeDto> dtoEntry : dtoMap.entrySet() ) {
                entityMap.put( DepartmentMapper.INSTANCE.toSecretaryEntity( dtoEntry.getKey() ) ,
                    DepartmentMapper.INSTANCE.toEmployeeEntity( dtoEntry.getValue() )  );
            }
        }
    }
}
