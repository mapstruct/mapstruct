/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2436;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue2436ConstructorMapper {

    Issue2436ConstructorMapper INSTANCE = Mappers.getMapper( Issue2436ConstructorMapper.class );

    @Mapping(target = "property1", source = "source1.property1", defaultValue = "default1")
    @Mapping(target = "property2", source = "source2.property2", defaultValue = "default2")
    ConstructorTarget map(Source1 source1, Source2 source2);
}
