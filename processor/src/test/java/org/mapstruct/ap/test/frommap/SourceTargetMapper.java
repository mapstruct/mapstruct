/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Map;

/**
 * @author Christian Kosmowski
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mapping(source = "theInt", target = "normalInt")
    @Mapping(target = "finalList", ignore = true)
    @Mapping(target = "normalList", ignore = true)
    @Mapping(target = "fieldOnlyWithGetter", ignore = true)
    Target toTarget(Map<String, Object> source);

    @Mapping(source = "source.theInt", target = "normalInt")
    Target toTarget(Map<String, Object> source, Source source2);

    default String map(Object object) {
        return object.toString();
    }

}
