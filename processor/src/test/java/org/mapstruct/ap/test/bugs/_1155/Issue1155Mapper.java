/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1155;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1155Mapper {

    Issue1155Mapper INSTANCE = Mappers.getMapper( Issue1155Mapper.class );

    @Mapping(source = "clientId", target = "client.id")
    Entity toEntity(Entity.Dto dto);
}
