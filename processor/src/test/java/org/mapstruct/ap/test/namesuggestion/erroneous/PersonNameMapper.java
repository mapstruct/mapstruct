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
public interface PersonNameMapper {

    PersonNameMapper MAPPER = Mappers.getMapper( PersonNameMapper.class );

    @Mapping(source = "name", target = "fulName")
    Person mapPerson(PersonDto dto);

}
