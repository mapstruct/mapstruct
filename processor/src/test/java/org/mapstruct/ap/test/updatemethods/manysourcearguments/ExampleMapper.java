/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.manysourcearguments;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExampleMapper {

    ExampleMapper INSTANCE = Mappers.getMapper( ExampleMapper.class );

    void update(@MappingTarget ExampleTarget target, ExampleSource source, ExampleMember member);

}
