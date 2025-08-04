/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignoreunmapped.mapper.erroneous;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ap.test.ignoreunmapped.UserDto;
import org.mapstruct.ap.test.ignoreunmapped.UserEntity;

@Mapper
public interface UserMapperWithMultiMapping {
    @BeanMapping(ignoreUnmappedSourceProperties = {"password", "email", "username"},
            unmappedSourcePolicy = ReportingPolicy.ERROR)
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    UserDto map(UserEntity user);
}
