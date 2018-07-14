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
public interface ErroneousAddressMapperWithCyclicDependency {

    ErroneousAddressMapperWithCyclicDependency INSTANCE =
        Mappers.getMapper( ErroneousAddressMapperWithCyclicDependency.class );

    @Mappings({
        @Mapping(target = "lastName", dependsOn = "middleName"),
        @Mapping(target = "middleName", dependsOn = "firstName"),
        @Mapping(target = "firstName", dependsOn = "lastName")
    })
    PersonDto personToDto(Person person);
}
