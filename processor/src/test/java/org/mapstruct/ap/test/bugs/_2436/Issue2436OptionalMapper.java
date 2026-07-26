/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2436;

import java.util.Optional;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue2436OptionalMapper {

    Issue2436OptionalMapper INSTANCE = Mappers.getMapper( Issue2436OptionalMapper.class );

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Mapping(target = "property1", source = "source1.property1", defaultValue = "default1")
    @Mapping(target = "property2", ignore = true)
    @Mapping(target = "property3", ignore = true)
    Target map(Optional<Source1> source1);
}
