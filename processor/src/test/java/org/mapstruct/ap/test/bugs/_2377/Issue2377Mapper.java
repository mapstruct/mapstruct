/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2377;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(uses = NullableHelper.class)
public interface Issue2377Mapper {

    Issue2377Mapper INSTANCE = Mappers.getMapper( Issue2377Mapper.class );

    Request map(RequestDto dto);

    List<Request.ChildRequest> mapChildren(List<RequestDto.ChildRequestDto> dtos);
}
