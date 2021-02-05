/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2352.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.bugs._2352.dto.TheDto;
import org.mapstruct.ap.test.bugs._2352.dto.TheModel;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface TheModelMapper {

    TheDto convert(TheModel theModel);
}
