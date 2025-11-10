/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.protobuf;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.itest.protobuf.Everything;
import org.mapstruct.itest.protobuf.EverythingModel;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(nullValueCheckStrategy = ALWAYS, unmappedTargetPolicy = ERROR, unmappedSourcePolicy = ERROR)
public interface EverythingMapper {

    EverythingMapper INSTANCE = Mappers.getMapper( EverythingMapper.class );

    @Mapping(target = "float_", source = "float")
    @Mapping(target = "double_", source = "double")
    @Mapping(target = "enum_", source = "enum")
    Everything modelToEntity(EverythingModel model);

    @Mapping(target = "float", source = "float_")
    @Mapping(target = "double", source = "double_")
    @Mapping(target = "enum", source = "enum_")
    EverythingModel entityToModel(Everything entity);
}
