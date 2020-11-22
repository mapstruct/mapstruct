/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.defaultvalue;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface CountryMapperMultipleSources {

    CountryMapperMultipleSources INSTANCE = Mappers.getMapper( CountryMapperMultipleSources.class );

    @Mapping(target = "code", defaultValue = "CH")
    @Mapping(target = "region", source = "regionCode")
    CountryDts map(CountryEntity entity, String regionCode);
}
