/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2109;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2109Mapper {

    Issue2109Mapper INSTANCE = Mappers.getMapper( Issue2109Mapper.class );

    Target map(Source source);

    @Mapping(target = "data", defaultExpression = "java(new byte[0])")
    Target mapWithEmptyData(Source source);
}
