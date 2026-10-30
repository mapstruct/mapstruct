/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3896;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue3896Mapper {

    Issue3896Mapper INSTANCE = Mappers.getMapper( Issue3896Mapper.class );

    @Mapping(target = "groupState", source = "group.state")
    User fromEntity(UserEntity data);

    default GroupState toGroupState(int code) {
        return GroupState.fromCode( code );
    }

    default UserState toUserState(int code) {
        return UserState.fromCode( code );
    }
}
