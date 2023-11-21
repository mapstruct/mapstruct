/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.nested;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OptionalNestedMapper {

    OptionalNestedMapper INSTANCE = Mappers.getMapper( OptionalNestedMapper.class );

    @Mapping(source = "optionalToNonOptional?.value?", target = "optionalToNonOptional")
    @Mapping(source = "optionalToOptional?.value", target = "optionalToOptional")
    @Mapping(source = "nonOptionalToNonOptional?.value", target = "nonOptionalToNonOptional")
    @Mapping(source = "nonOptionalToOptional?.value", target = "nonOptionalToOptional")
    Target toTarget(Source source);

}
