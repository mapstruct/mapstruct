/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2352.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.bugs._2352.dto.TheDto;
import org.mapstruct.ap.test.bugs._2352.dto.TheModels;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(uses = TheModelMapper.class)
public interface TheModelsMapper {

    TheModelsMapper INSTANCE = Mappers.getMapper( TheModelsMapper.class );

    List<TheDto> convert(TheModels theModels);
}
