/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1738;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1738Mapper {

    Issue1738Mapper INSTANCE = Mappers.getMapper( Issue1738Mapper.class );

    @Mapping(target = "value", source = "nested.value")
    Target map(Source source);
}
