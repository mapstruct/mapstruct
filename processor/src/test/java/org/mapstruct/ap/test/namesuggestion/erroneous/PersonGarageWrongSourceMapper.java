/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.namesuggestion.erroneous;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.namesuggestion.Person;
import org.mapstruct.ap.test.namesuggestion.PersonDto;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonGarageWrongSourceMapper {

    PersonGarageWrongSourceMapper MAPPER = Mappers.getMapper( PersonGarageWrongSourceMapper.class );

    @Mappings( {
        @Mapping( target = "garage.color.rgb", source = "garage.colour.rgb" ),
        @Mapping( target = "fullAge", source = "age" ),
        @Mapping( target = "fullName", source = "name" )
    } )
    Person mapPerson(PersonDto dto);

    @Mappings( {
        @Mapping( target = "garage.color", source = "garage.colour" ),
        @Mapping( target = "fullAge", source = "age" ),
        @Mapping( target = "fullName", source = "name" )
    } )
    Person mapPersonGarage(PersonDto dto);

}
