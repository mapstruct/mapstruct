/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3949;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface Issue3949InterfaceMapper {

    Issue3949InterfaceMapper INSTANCE = Mappers.getMapper( Issue3949InterfaceMapper.class );

    void overwriteDate(@MappingTarget TargetDateInterface target, DateSource dateSource);

    void overwriteString(@MappingTarget TargetStringInterface target, StringSource stringSource);

    void overwriteDateWithConversion(@MappingTarget TargetDateInterface target, StringSource dateSource);

    void overwriteStringWithConversion(@MappingTarget TargetStringInterface target, DateSource stringSource);

    void updateParent(@MappingTarget ParentTargetInterface target, ParentSource source);
}
