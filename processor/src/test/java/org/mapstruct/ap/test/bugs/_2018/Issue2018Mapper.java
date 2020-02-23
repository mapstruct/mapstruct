/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2018;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2018Mapper {

    Issue2018Mapper INSTANCE = Mappers.getMapper( Issue2018Mapper.class );

    @Mapping(target = "some_value", source = "someValue")
    Target map(Source source);
}
