/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.constructorproperties;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface SimpleConstructorPropertiesMapper {

    SimpleConstructorPropertiesMapper INSTANCE = Mappers.getMapper( SimpleConstructorPropertiesMapper.class );

    PersonWithConstructorProperties map(PersonDto dto);

    @Mapping(target = "age", constant = "25")
    @Mapping(target = "job", constant = "Software Developer")
    PersonWithConstructorProperties mapWithConstants(PersonDto dto);

    @Mapping(target = "age", expression = "java(25 - 5)")
    @Mapping(target = "job", expression = "java(\"Software Developer\".toLowerCase())")
    PersonWithConstructorProperties mapWithExpression(PersonDto dto);
}
