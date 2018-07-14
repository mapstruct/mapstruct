/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.exceptions;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.nestedbeans.exceptions._target.ProjectDto;
import org.mapstruct.ap.test.nestedbeans.exceptions.source.Project;

/**
 * @author Filip Hrisafov
 * @author Darren Rambaud
 */
@Mapper(uses = EntityFactory.class)
public interface ProjectMapper {

    Project map(ProjectDto source) throws MappingException;
}
