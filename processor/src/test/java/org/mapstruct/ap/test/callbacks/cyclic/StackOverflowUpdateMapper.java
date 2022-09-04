/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.cyclic;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public interface StackOverflowUpdateMapper {

    StackOverflowUpdateMapper INSTANCE = Mappers.getMapper( StackOverflowUpdateMapper.class );

    void map(Teacher teacher, @MappingTarget TeacherDto teacherDto);
}
