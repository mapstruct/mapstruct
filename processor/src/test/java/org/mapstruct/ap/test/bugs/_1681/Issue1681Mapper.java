/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1681;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1681Mapper {

    Issue1681Mapper INSTANCE = Mappers.getMapper( Issue1681Mapper.class );

    Target update(@MappingTarget Target target, Source source);

    @Mapping(target = "builderValue", source = "value")
    Target update(@MappingTarget Target.Builder targetBuilder, Source source);
}
