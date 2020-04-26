/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.defaultannotated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface SimpleDefaultAnnotatedConstructorMapper {

    SimpleDefaultAnnotatedConstructorMapper INSTANCE =
        Mappers.getMapper( SimpleDefaultAnnotatedConstructorMapper.class );

    PersonWithDefaultAnnotatedConstructor map(PersonDto dto);

    @Mapping(target = "age", constant = "25")
    PersonWithDefaultAnnotatedConstructor mapWithConstants(PersonDto dto);

    @Mapping(target = "age", expression = "java(25 - 5)")
    PersonWithDefaultAnnotatedConstructor mapWithExpression(PersonDto dto);
}
