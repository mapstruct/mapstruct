/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2023;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2023Mapper {

    Issue2023Mapper INSTANCE = Mappers.getMapper( Issue2023Mapper.class );

    @Mapping(target = "correlationId", source = "correlationId", defaultExpression = "java(UUID.randomUUID())")
    NewPersonRequest createRequest(PersonDto person, UUID correlationId);
}
