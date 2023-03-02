/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OptionalTestMapper {

    OptionalTestMapper INSTANCE = Mappers.getMapper( OptionalTestMapper.class );

    Target map(Source source);

    @InheritInverseConfiguration
    Source map(Target target);

    @Mapping(source = "a", target = "b")
    Target.SubType map(Source.SubType source);

    @InheritInverseConfiguration
    Source.SubType map(Target.SubType source);

}
