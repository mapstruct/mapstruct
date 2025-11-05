/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.protobuf;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.protobuf._target.SimpleUserDto;
import org.mapstruct.ap.test.protobuf.source.SimpleUserProto;
import org.mapstruct.factory.Mappers;

/**
 * Simple mapper for testing protobuf repeated field support.
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface SimpleProtobufMapper {

    SimpleProtobufMapper INSTANCE = Mappers.getMapper(SimpleProtobufMapper.class);

    /**
     * Maps from protobuf message to DTO.
     * Should recognize getItemsList() as getter for "items" property.
     */
    SimpleUserDto protoToDto(SimpleUserProto proto);
}
