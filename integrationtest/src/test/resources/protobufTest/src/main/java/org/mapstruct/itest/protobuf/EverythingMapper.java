/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.protobuf;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.mapstruct.itest.protobuf.Everything;

import static org.mapstruct.CollectionMappingStrategy.TARGET_IMMUTABLE;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;
import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(
    nullValueCheckStrategy = ALWAYS,
    nullValuePropertyMappingStrategy = IGNORE,
    collectionMappingStrategy = TARGET_IMMUTABLE,
    unmappedTargetPolicy = ERROR,
    unmappedSourcePolicy = ERROR)
public abstract class EverythingMapper {

    public static final EverythingMapper INSTANCE = Mappers.getMapper( EverythingMapper.class );

    // proto2
    @Mapping(target = "float_", source = "float")
    @Mapping(target = "double_", source = "double")
    @Mapping(target = "enum_", source = "enum")
    public abstract Everything proto2ToJavaBean(EverythingProto2 proto);

    @Mapping(target = "float", source = "float_")
    @Mapping(target = "double", source = "double_")
    @Mapping(target = "enum", source = "enum_")
    public abstract EverythingProto2 javaBeanToProto2(Everything javaBean);

    @Mapping(target = "float", source = "float_")
    @Mapping(target = "double", source = "double_")
    @Mapping(target = "enum", source = "enum_")
    public abstract void updateProto2FromJavaBean(@MappingTarget EverythingProto2.Builder proto, Everything javaBean);

    @Mapping(target = "float_", source = "float")
    @Mapping(target = "double_", source = "double")
    @Mapping(target = "enum_", source = "enum")
    public abstract void updateJavaBeanFromProto2(@MappingTarget Everything javaBean, EverythingProto2 proto);

    // proto3
    @Mapping(target = "float_", source = "float")
    @Mapping(target = "double_", source = "double")
    @Mapping(target = "enum_", source = "enum")
    public abstract Everything proto3ToJavaBean(EverythingProto3 proto);

    @Mapping(target = "float", source = "float_")
    @Mapping(target = "double", source = "double_")
    @Mapping(target = "enum", source = "enum_")
    public abstract EverythingProto3 javaBeanToProto3(Everything javaBean);

    @Mapping(target = "float", source = "float_")
    @Mapping(target = "double", source = "double_")
    @Mapping(target = "enum", source = "enum_")
    public abstract void updateProto3FromJavaBean(@MappingTarget EverythingProto3.Builder proto, Everything javaBean);

    @Mapping(target = "float_", source = "float")
    @Mapping(target = "double_", source = "double")
    @Mapping(target = "enum_", source = "enum")
    public abstract void updateJavaBeanFromProto3(@MappingTarget Everything javaBean, EverythingProto3 proto);

    // edition 2023
    @Mapping(target = "float_", source = "float")
    @Mapping(target = "double_", source = "double")
    @Mapping(target = "enum_", source = "enum")
    public abstract Everything edition2023ToJavaBean(EverythingEdition2023 proto);

    @Mapping(target = "float", source = "float_")
    @Mapping(target = "double", source = "double_")
    @Mapping(target = "enum", source = "enum_")
    public abstract EverythingEdition2023 javaBeanToEdition2023(Everything javaBean);

    @Mapping(target = "float", source = "float_")
    @Mapping(target = "double", source = "double_")
    @Mapping(target = "enum", source = "enum_")
    public abstract void updateEdition2023FromJavaBean(
        @MappingTarget EverythingEdition2023.Builder proto, Everything javaBean);

    @Mapping(target = "float_", source = "float")
    @Mapping(target = "double_", source = "double")
    @Mapping(target = "enum_", source = "enum")
    public abstract void updateJavaBeanFromEdition2023(@MappingTarget Everything javaBean, EverythingEdition2023 proto);

    protected EverythingProto2.Message javaBeanMessageToProto2Message(Everything.Message value) {
        if ( value == null ) {
            return null;
        }
        var builder = EverythingProto2.Message.newBuilder();
        builder.setId( value.getId() );
        builder.setName( value.getName() );
        return builder.build();
    }

    protected Everything.Message proto2MessageToJavaBeanMessage(EverythingProto2.Message value) {
        if ( value == null ) {
            return null;
        }
        Everything.Message message = new Everything.Message();
        message.setId( value.getId() );
        message.setName( value.getName() );
        return message;
    }

    protected EverythingProto3.Message javaBeanMessageToProto3Message(Everything.Message value) {
        if ( value == null ) {
            return null;
        }
        var builder = EverythingProto3.Message.newBuilder();
        builder.setId( value.getId() );
        builder.setName( value.getName() );
        return builder.build();
    }

    protected Everything.Message proto3MessageToJavaBeanMessage(EverythingProto3.Message value) {
        if ( value == null ) {
            return null;
        }
        Everything.Message message = new Everything.Message();
        message.setId( value.getId() );
        message.setName( value.getName() );
        return message;
    }

    protected EverythingEdition2023.Message javaBeanMessageToEdition2023Message(Everything.Message value) {
        if ( value == null ) {
            return null;
        }
        var builder = EverythingEdition2023.Message.newBuilder();
        builder.setId( value.getId() );
        builder.setName( value.getName() );
        return builder.build();
    }

    protected Everything.Message edition2023MessageToJavaBeanMessage(EverythingEdition2023.Message value) {
        if ( value == null ) {
            return null;
        }
        Everything.Message message = new Everything.Message();
        message.setId( value.getId() );
        message.setName( value.getName() );
        return message;
    }
}
