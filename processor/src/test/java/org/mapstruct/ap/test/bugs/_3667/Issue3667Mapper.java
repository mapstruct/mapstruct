/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3667;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue3667Mapper {

    Issue3667Mapper INSTANCE = Mappers.getMapper( Issue3667Mapper.class );

    @Mapping(target = "nested.value", source = "nested.nested1.value")
    Target mapFirst(Source source);

    @Mapping(target = "nested.value", source = "nested.nested2.value")
    Target mapSecond(Source source);
}
