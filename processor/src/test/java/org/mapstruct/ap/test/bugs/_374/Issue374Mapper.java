/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._374;

import java.util.List;
import java.util.Map;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
public interface Issue374Mapper {

    Issue374Mapper INSTANCE = Mappers.getMapper( Issue374Mapper.class );

    @Mapping(target = "constant", constant = "test")
    Target map(Source source, @MappingTarget Target target);

    @BeanMapping( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL )
    @Mapping(target = "constant", constant = "test")
    Target map2(Source source, @MappingTarget Target target);

    List<String> mapIterable(List<String> source, @MappingTarget List<String> target);

    Map<Integer, String> mapMap(Map<Integer, String> source, @MappingTarget Map<Integer, String> target);
}
