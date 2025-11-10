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

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(nullValueCheckStrategy = ALWAYS, unmappedTargetPolicy = ERROR, unmappedSourcePolicy = ERROR)
public interface EverythingMapper {

    EverythingMapper INSTANCE = Mappers.getMapper( EverythingMapper.class );

    // proto2
    @Mapping(target = "float_", source = "float")
    @Mapping(target = "double_", source = "double")
    @Mapping(target = "enum_", source = "enum")
    Everything proto2ToJavaBean(EverythingProto2 proto);

    @Mapping(target = "float", source = "float_")
    @Mapping(target = "double", source = "double_")
    @Mapping(target = "enum", source = "enum_")
    EverythingProto2 javaBeanToProto2(Everything javaBean);

    // proto3
    @Mapping(target = "float_", source = "float")
    @Mapping(target = "double_", source = "double")
    @Mapping(target = "enum_", source = "enum")
    Everything proto3ToJavaBean(EverythingProto3 proto);

    @Mapping(target = "float", source = "float_")
    @Mapping(target = "double", source = "double_")
    @Mapping(target = "enum", source = "enum_")
    EverythingProto3 javaBeanToProto3(Everything javaBean);

    // edition 2023
    @Mapping(target = "float_", source = "float")
    @Mapping(target = "double_", source = "double")
    @Mapping(target = "enum_", source = "enum")
    Everything edition2023ToJavaBean(EverythingEdition2023 proto);

    @Mapping(target = "float", source = "float_")
    @Mapping(target = "double", source = "double_")
    @Mapping(target = "enum", source = "enum_")
    EverythingEdition2023 javaBeanToEdition2023(Everything javaBean);
}
