/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.mappingTarget.simple;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleImmutableUpdateMapper {

    SimpleImmutableUpdateMapper INSTANCE = Mappers.getMapper( SimpleImmutableUpdateMapper.class );

    // This method is fine as if the mapping target has setters it would use them, otherwise it won't
    void toImmutable(SimpleMutableSource source, @MappingTarget SimpleImmutableTarget target);
}
