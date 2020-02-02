/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConversionMapper {

    ConversionMapper INSTANCE = Mappers.getMapper( ConversionMapper.class );

    Fridge map(CoolBeerDTO in);

}
