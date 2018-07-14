/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserDtoUpdateMapperSmart {

    UserDtoUpdateMapperSmart INSTANCE = Mappers.getMapper( UserDtoUpdateMapperSmart.class );

    void userToUserDto(@MappingTarget UserDto userDto, User user);

}
