/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.differenttypes;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OptionalDifferentTypesMapper {

    OptionalDifferentTypesMapper INSTANCE = Mappers.getMapper(
        OptionalDifferentTypesMapper.class );

    Target toTarget(Source source);

}
