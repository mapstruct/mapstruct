/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.parentchild;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParentChildMapper {

    ParentChildMapper INSTANCE = Mappers.getMapper( ParentChildMapper.class );

    ImmutableParent toParent(MutableParent source);

    @Mapping(target = "name", source = "childName")
    ImmutableChild toChild(MutableChild source);
}
