/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nullvaluepropertytodefault;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface NullValuePropertyToDefaultMapper {

    NullValuePropertyToDefaultMapper INSTANCE = Mappers.getMapper(
        NullValuePropertyToDefaultMapper.class );

    void mapTarget(Source source, @MappingTarget Target target);

}
