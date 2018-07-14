/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.dependency;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousAddressMapperWithUnknownPropertyInDependsOn {

    ErroneousAddressMapperWithUnknownPropertyInDependsOn INSTANCE = Mappers.getMapper(
        ErroneousAddressMapperWithUnknownPropertyInDependsOn.class
    );

    @Mapping(target = "lastName", dependsOn = "doesnotexist")
    PersonDto personToDto(Person person);
}
