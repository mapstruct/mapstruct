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
        @Mapping(source = "senderId", target = "sender.nestedClient.id"),
        @Mapping(source = "recipientId", target = "recipient.nestedClient.id"),
        @Mapping(source = "sameLevel.client.id", target = "client.nestedClient.id"),
        @Mapping(source = "sameLevel2.client.id", target = "client2.nestedClient.id"),
        @Mapping(source = "level.client.id", target = "nested.id"),
        @Mapping(source = "level2.client.id", target = "nested2.id"),
        @Mapping(source = "nestedDto.id", target = "id"),
        @Mapping(source = "nestedDto2.id", target = "id2")
    })
    Entity toEntity(Entity.Dto dto);

    @Mappings({
        @Mapping(source = "dto2.senderId", target = "sender.nestedClient.id"),
        @Mapping(source = "dto1.recipientId", target = "recipient.nestedClient.id"),
        @Mapping(source = "dto1.sameLevel.client.id", target = "client.nestedClient.id"),
        @Mapping(source = "dto2.sameLevel2.client.id", target = "client2.nestedClient.id"),
        @Mapping(source = "dto1.level.client.id", target = "nested.id"),
        @Mapping(source = "dto2.level2.client.id", target = "nested2.id"),
        @Mapping(source = "dto1.nestedDto.id", target = "id"),
        @Mapping(source = "dto2.nestedDto2.id", target = "id2")
    })
    Entity toEntity(Entity.Dto dto1, Entity.Dto dto2);
}
