/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.spi;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface CustomThrowingCheeseMapper {

    CustomThrowingCheeseMapper INSTANCE = Mappers.getMapper( CustomThrowingCheeseMapper.class );

    CustomThrowingCheeseType map(CheeseType cheese);

    CheeseType map(CustomThrowingCheeseType cheese);
}
