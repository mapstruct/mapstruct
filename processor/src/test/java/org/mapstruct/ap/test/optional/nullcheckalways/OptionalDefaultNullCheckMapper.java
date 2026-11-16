/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nullcheckalways;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OptionalDefaultNullCheckMapper {

    OptionalDefaultNullCheckMapper INSTANCE = Mappers.getMapper( OptionalDefaultNullCheckMapper.class );

    Target toTarget(Source source);

}
