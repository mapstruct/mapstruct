/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bool;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( uses = YesNoMapper.class )
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper( PersonMapper.class );

    PersonDto personToDto(Person person);
}
