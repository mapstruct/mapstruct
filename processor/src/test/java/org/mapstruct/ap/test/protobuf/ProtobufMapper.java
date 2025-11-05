/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.protobuf;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.protobuf._target.UserDto;
import org.mapstruct.ap.test.protobuf._target.UserProtoBuilder;
import org.mapstruct.ap.test.protobuf.source.ItemDto;
import org.mapstruct.ap.test.protobuf.source.UserProto;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for testing protobuf support.
 * Tests:
 * - Protobuf repeated field (getItemsList) to DTO with adder (addItem)
 * - Protobuf map field (getAttributesMap) to DTO with putter (putAttribute)
 * - Reverse: DTO to protobuf builder with adder (addItems) and putter (putAttributes)
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ProtobufMapper {

    ProtobufMapper INSTANCE = Mappers.getMapper(ProtobufMapper.class);

    /**
     * Maps from protobuf message to DTO.
     * Should recognize:
     * - getItemsList() as getter for "items" property
     * - getAttributesMap() as getter for "attributes" property
     */
    UserDto protoToDto(UserProto proto);

    /**
     * Maps from DTO to protobuf builder.
     * Should recognize:
     * - addItems() as adder for "items" property
     * - putAttributes() as putter (adder) for "attributes" property
     */
    UserProtoBuilder dtoToProtoBuilder(ItemDto dto);
}
