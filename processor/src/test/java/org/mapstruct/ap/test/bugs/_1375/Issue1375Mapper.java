/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1375;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1375Mapper {

    Issue1375Mapper INSTANCE = Mappers.getMapper( Issue1375Mapper.class );

    @Mapping(target = "nested.value", source = "value")
    Target map(Source source);
}
