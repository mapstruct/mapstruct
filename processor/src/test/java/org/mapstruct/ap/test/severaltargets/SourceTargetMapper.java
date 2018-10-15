/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.severaltargets;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(uses = TimeAndFormatMapper.class)
public interface SourceTargetMapper {
    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mappings(
        {
            @Mapping(source = "name", target = "name1"),
            @Mapping(source = "name", target = "name2"),
            @Mapping(source = "timeAndFormat", target = "time"),
            @Mapping(source = "timeAndFormat", target = "format")
        })
    Target sourceToTarget(Source s);
}
