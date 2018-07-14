/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.selection;

import javax.annotation.Generated;
import org.mapstruct.ap.test.updatemethods.DepartmentDto;
import org.mapstruct.ap.test.updatemethods.DepartmentEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-12-30T19:17:50+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class ExternalMapperImpl implements ExternalMapper {

    @Override
    public void toDepartmentEntity(DepartmentDto dto, DepartmentEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setName( dto.getName() );
    }
}
