/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.simple;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.constructor.Person;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface SimpleConstructorMapper {

    SimpleConstructorMapper INSTANCE = Mappers.getMapper( SimpleConstructorMapper.class );

    Person map(PersonDto dto);

    @Mapping(target = "age", constant = "25")
    @Mapping(target = "job", constant = "Software Developer")
    Person mapWithConstants(PersonDto dto);

    @Mapping(target = "age", expression = "java(25 - 5)")
    @Mapping(target = "job", expression = "java(\"Software Developer\".toLowerCase())")
    Person mapWithExpression(PersonDto dto);
}
