/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.dependency;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper( AddressMapper.class );

    @Mappings({
        @Mapping(target = "surName", source = "lastName", dependsOn = "middleName"),
        @Mapping(target = "middleName", dependsOn = "givenName"),
        @Mapping(target = "givenName", source = "firstName")
    })
    AddressDto addressToDto(Address address);

    @Mappings({
        @Mapping(target = "lastName", dependsOn = { "firstName", "middleName" })
    })
    PersonDto personToDto(Person person);
}
