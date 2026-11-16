/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.kotlin.data;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface DefaultPropertyMapper {

    DefaultPropertyMapper INSTANCE = Mappers.getMapper( DefaultPropertyMapper.class );

    DefaultPropertyTarget map(DefaultPropertySource source);

    DefaultPropertySource map(DefaultPropertyTarget target);
}
