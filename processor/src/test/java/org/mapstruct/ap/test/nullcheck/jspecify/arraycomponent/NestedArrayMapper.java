/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.arraycomponent;

import org.jspecify.annotations.NullMarked;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@NullMarked
@Mapper
public interface NestedArrayMapper {

    NestedArrayMapper INSTANCE = Mappers.getMapper( NestedArrayMapper.class );

    @Mapping( target = "ids", source = "child.tags" )
    NestedArrayTarget map(NestedArraySource source);
}
