/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDtoMapperSmart {

    UserDtoMapperSmart INSTANCE = Mappers.getMapper( UserDtoMapperSmart.class );

    UserDto userToUserDto(User user);

    org.mapstruct.ap.test.nestedbeans.other.UserDto userToUserDto2( User user );
}
