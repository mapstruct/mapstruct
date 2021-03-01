/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1148;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1148Mapper {

    Issue1148Mapper INSTANCE = Mappers.getMapper( Issue1148Mapper.class );

    @Mappings({
        @Mapping(target = "sender.nestedClient.id", source = "senderId"),
        @Mapping(target = "recipient.nestedClient.id", source = "recipientId"),
        @Mapping(target = "client.nestedClient.id", source = "sameLevel.client.id"),
        @Mapping(target = "client2.nestedClient.id", source = "sameLevel2.client.id"),
        @Mapping(target = "nested.id", source = "level.client.id"),
        @Mapping(target = "nested2.id", source = "level2.client.id"),
        @Mapping(target = "id", source = "nestedDto.id"),
        @Mapping(target = "id2", source = "nestedDto2.id")
    })
    Entity toEntity(Entity.Dto dto);

    @Mappings({
        @Mapping(target = "sender.nestedClient.id", source = "dto2.senderId"),
        @Mapping(target = "recipient.nestedClient.id", source = "dto1.recipientId"),
        @Mapping(target = "client.nestedClient.id", source = "dto1.sameLevel.client.id"),
        @Mapping(target = "client2.nestedClient.id", source = "dto2.sameLevel2.client.id"),
        @Mapping(target = "nested.id", source = "dto1.level.client.id"),
        @Mapping(target = "nested2.id", source = "dto2.level2.client.id"),
        @Mapping(target = "id", source = "dto1.nestedDto.id"),
        @Mapping(target = "id2", source = "dto2.nestedDto2.id")
    })
    Entity toEntity(Entity.Dto dto1, Entity.Dto dto2);
}
