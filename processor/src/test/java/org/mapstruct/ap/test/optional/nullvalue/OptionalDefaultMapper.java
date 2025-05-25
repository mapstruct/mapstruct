/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nullvalue;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Dennis Melzer
 */
@Mapper
public interface OptionalDefaultMapper {

    OptionalDefaultMapper INSTANCE = Mappers.getMapper( OptionalDefaultMapper.class );

    Target map(Source source);
}
