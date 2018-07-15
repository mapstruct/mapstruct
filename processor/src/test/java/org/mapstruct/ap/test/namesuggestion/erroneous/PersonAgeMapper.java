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
public interface PersonAgeMapper {

    PersonAgeMapper MAPPER = Mappers.getMapper( PersonAgeMapper.class );

    @Mapping(source = "agee", target = "fullAge")
    Person mapPerson(PersonDto dto);

}
