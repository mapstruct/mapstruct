/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.namesuggestion.erroneous;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.namesuggestion.Person;
import org.mapstruct.ap.test.namesuggestion.PersonDto;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonGarageWrongTargetMapper {

    PersonGarageWrongTargetMapper MAPPER = Mappers.getMapper( PersonGarageWrongTargetMapper.class );

    @Mapping(source = "garage.color.rgb", target = "garage.colour.rgb")
    Person mapPerson(PersonDto dto);

    @Mapping(source = "garage.color", target = "garage.colour")
    Person mapPersonGarage(PersonDto dto);
}
