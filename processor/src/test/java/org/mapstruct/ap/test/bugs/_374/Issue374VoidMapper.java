/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._374;

import java.util.List;
import java.util.Map;

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
public interface Issue374VoidMapper {

    Issue374VoidMapper INSTANCE = Mappers.getMapper( Issue374VoidMapper.class );

    @Mapping(target = "constant", constant = "test")
    void map(Source source, @MappingTarget Target target);

    void mapIterable(List<String> source, @MappingTarget List<String> target);

    void mapMap(Map<Integer, String> source, @MappingTarget Map<Integer, String> target);
}
