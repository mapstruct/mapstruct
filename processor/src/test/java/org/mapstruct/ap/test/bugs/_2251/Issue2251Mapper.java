/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2251;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue2251Mapper {

    Issue2251Mapper INSTANCE = Mappers.getMapper( Issue2251Mapper.class );

    @Mapping(target = "value1", source = "source.value")
    Target map(Source source, String value2);

}
