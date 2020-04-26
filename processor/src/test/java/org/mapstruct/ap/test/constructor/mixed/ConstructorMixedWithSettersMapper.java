/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.mixed;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ConstructorMixedWithSettersMapper {

    ConstructorMixedWithSettersMapper INSTANCE = Mappers.getMapper( ConstructorMixedWithSettersMapper.class );

    PersonMixed map(PersonDto dto);
}
