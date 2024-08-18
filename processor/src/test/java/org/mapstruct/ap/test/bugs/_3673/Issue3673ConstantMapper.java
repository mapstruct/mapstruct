/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3673;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue3673ConstantMapper {

    Issue3673ConstantMapper INSTANCE = Mappers.getMapper( Issue3673ConstantMapper.class );

    @Mapping(target = "details.name", source = "details.name")
    @Mapping(target = "details.type", constant = "DOG")
    Animal map(Dog dog);

    @Mapping(target = "details.name", source = "details.name")
    @Mapping(target = "details.type", constant = "CAT")
    Animal map(Cat cat);

}
