/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.multiple;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.builder.multiple.build.Process;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(config = BuilderMapperConfig.class)
public interface BuilderConfigDefinedMapper {

    BuilderConfigDefinedMapper INSTANCE = Mappers.getMapper( BuilderConfigDefinedMapper.class );

    Process map(Source source);

    @BeanMapping(builder = @Builder(buildMethod = "wrongCreate"))
    Process wrongMap(Source source);
}
