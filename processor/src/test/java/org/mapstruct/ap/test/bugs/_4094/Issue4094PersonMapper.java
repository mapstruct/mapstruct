/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4094;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Nested mapper used by {@link Issue4094Mapper} (mirrors the issue report's PersonMapper).
 */
@Mapper
public interface Issue4094PersonMapper {

    Issue4094PersonMapper INSTANCE = Mappers.getMapper( Issue4094PersonMapper.class );

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "genderCode", source = "gender")
    @BeanMapping(ignoreByDefault = true)
    Issue4094Mapper.PersonDto toDto(Issue4094Mapper.Person person);
}
