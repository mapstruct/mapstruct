/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.java8;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Java8Mapper {

    Java8Mapper INSTANCE = Mappers.getMapper( Java8Mapper.class );

    @Mapping(target = "givenName", source = "firstName")
    @Mapping(target = "surname", source = "lastName")
    Target sourceToTarget(Source source);
}
