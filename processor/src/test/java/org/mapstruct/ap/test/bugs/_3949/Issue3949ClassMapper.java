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
public interface Issue3949ClassMapper {

    Issue3949ClassMapper INSTANCE = Mappers.getMapper(Issue3949ClassMapper.class);

    void overwriteDate(@MappingTarget TargetDate target, DateSource dateSource);

    void overwriteString(@MappingTarget TargetString target, StringSource stringSource);

    void overwriteDateWithConversion(@MappingTarget TargetDate target, StringSource dateSource);

    void overwriteStringWithConversion(@MappingTarget TargetString target, DateSource stringSource);

    void updateParent(@MappingTarget ParentTarget target, ParentSource source);
}
