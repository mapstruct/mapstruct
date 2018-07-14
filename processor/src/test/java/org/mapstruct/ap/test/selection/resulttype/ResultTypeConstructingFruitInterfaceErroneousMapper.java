/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.resulttype;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Filip Hrisafov
 */
@Mapper
public interface ResultTypeConstructingFruitInterfaceErroneousMapper {

    ResultTypeConstructingFruitInterfaceErroneousMapper INSTANCE = Mappers.getMapper(
        ResultTypeConstructingFruitInterfaceErroneousMapper.class );

    @Mapping(target = "type", ignore = true)
    IsFruit map(FruitDto source);
}
