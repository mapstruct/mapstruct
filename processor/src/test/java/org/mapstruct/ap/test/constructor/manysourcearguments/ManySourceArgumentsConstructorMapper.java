/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.manysourcearguments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ap.test.constructor.Person;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ManySourceArgumentsConstructorMapper {

    ManySourceArgumentsConstructorMapper INSTANCE = Mappers.getMapper( ManySourceArgumentsConstructorMapper.class );

    @Mapping(target = "name", defaultValue = "Tester")
    @Mapping(target = "city", defaultExpression = "java(\"Zurich\")")
    Person map(String name, String city);
}
