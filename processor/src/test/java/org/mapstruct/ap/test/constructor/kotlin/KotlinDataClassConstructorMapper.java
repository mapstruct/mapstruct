/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.kotlin;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.factory.Mappers;

@Mapper
public interface KotlinDataClassConstructorMapper {

    KotlinDataClassConstructorMapper INSTANCE = Mappers.getMapper( KotlinDataClassConstructorMapper.class );

    PersonDataClass map(PersonDto dto);
}
