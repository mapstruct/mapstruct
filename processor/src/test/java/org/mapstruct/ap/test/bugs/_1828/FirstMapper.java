/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1828;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FirstMapper {

    FirstMapper INSTANCE = Mappers.getMapper( FirstMapper.class );

    @Mapping(target = "completeAddress.lineOne", source = "specialAddress.line1")
    @Mapping(target = "completeAddress.lineTwo", source = "specialAddress.line2")
    @Mapping(target = "completeAddress.city", source = "generalAddress.city")
    @Mapping(target = "completeAddress.country", source = "generalAddress.country")
    Person mapPerson(Employee employee);

}
