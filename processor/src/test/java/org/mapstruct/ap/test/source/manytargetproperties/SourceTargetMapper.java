/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.manytargetproperties;

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
            @Mapping(target = "name1", source = "name"),
            @Mapping(target = "name2", source = "name"),
            @Mapping(target = "time", source = "timeAndFormat"),
            @Mapping(target = "format", source = "timeAndFormat")
        })
    Target sourceToTarget(Source s);
}
