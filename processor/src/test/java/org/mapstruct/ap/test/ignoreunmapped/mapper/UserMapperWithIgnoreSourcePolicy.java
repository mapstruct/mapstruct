/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignoreunmapped.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ap.test.ignoreunmapped.UserDto;
import org.mapstruct.ap.test.ignoreunmapped.UserEntity;

@Mapper
public interface UserMapperWithIgnoreSourcePolicy {

    @BeanMapping(ignoreUnmappedSourceProperties = {"password", "email"}, unmappedSourcePolicy = ReportingPolicy.IGNORE)
    @Mapping(source = "email", target = "email")
    UserDto map(UserEntity user);
}
