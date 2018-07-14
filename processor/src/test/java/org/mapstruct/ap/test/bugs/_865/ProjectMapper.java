/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._865;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper( ProjectMapper.class );

    @Mapping(target = "coreUsers", source = "projectMembers")
    void mapProjectUpdate(ProjectDto dto, @MappingTarget ProjectEntity entity);

    @Mapping(target = "coreUsers", source = "projectMembers")
    void mapProjectUpdateWithoutGetter(ProjectDto dto, @MappingTarget ProjectEntityWithoutSetter entity);

    ProjCoreUserEntity mapUser(ProjMemberDto dto);
}
