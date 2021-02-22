/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.defaultcomponentmodel;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface NonInstanceMapper {

    NonInstanceMapper MAPPER = Mappers.getMapper( NonInstanceMapper.class );

    Target map(Source source);
}
