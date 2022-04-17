/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2538;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeamMapper {

    TeamMapper INSTANCE = Mappers.getMapper( TeamMapper.class );

    // This method is testing methodX(methodY(...)) where methodY is qualified
    @Mapping(target = "group", source = "groupId", qualifiedByName = "firstLookup")
    TeamRole mapUsingFirstLookup(TeamRoleDto in);

    // This method is testing methodX(methodY(...)) where methodX is qualified
    @Mapping(target = "group", source = "groupId", qualifiedByName = "secondLookup")
    TeamRole mapUsingSecondLookup(TeamRoleDto in);

    Group map(GroupDto in);

    @Named("firstLookup")
    default GroupDto lookupGroup(String groupId) {
        return groupId != null ? new GroupDto( "lookup-" + groupId ) : null;
    }

    default GroupDto normalLookup(String groupId) {
        return groupId != null ? new GroupDto( groupId ) : null;
    }

    @Named("secondLookup")
    default Group mapSecondLookup(GroupDto in) {
        return in != null ? new Group( "second-" + in.getId() ) : null;
    }

}
