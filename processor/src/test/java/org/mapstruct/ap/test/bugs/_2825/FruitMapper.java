/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2825;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

/**
 * @author orange add
 */

@Mapper(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)

public interface FruitMapper {

    FruitMapper INSTANCE = Mappers.getMapper( FruitMapper.class );

    @SubclassMapping(target = AppleDto.class, source = Apple.class)
    @SubclassMapping(target = OrangeDto.class, source = Orange.class)
    FruitDto map(Fruit fruit);
}
