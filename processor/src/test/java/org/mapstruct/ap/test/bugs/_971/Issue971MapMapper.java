/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._971;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue971MapMapper {

    Issue971MapMapper INSTANCE = Mappers.getMapper( Issue971MapMapper.class );

    MapSource mapTargetToSource(MapTarget source);

}
