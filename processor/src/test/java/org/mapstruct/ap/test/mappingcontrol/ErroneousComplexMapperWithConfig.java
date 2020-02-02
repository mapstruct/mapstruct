/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = Config.class)
public interface ErroneousComplexMapperWithConfig {

    ErroneousComplexMapperWithConfig INSTANCE = Mappers.getMapper( ErroneousComplexMapperWithConfig.class );

    @Mapping(target = "beerCount", source = "shelve")
    Fridge map(FridgeDTO in);

    default String toBeerCount(ShelveDTO in) {
        return in.getCoolBeer().getBeerCount();
    }
}
