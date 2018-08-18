/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1552;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1552Mapper {

    Issue1552Mapper INSTANCE = Mappers.getMapper( Issue1552Mapper.class );

    @Mappings({
        @Mapping(target = "first.value", constant = "constant"),
        @Mapping(target = "second.value", source = "sourceTwo")
    })
    Target twoArgsWithConstant(String sourceOne, String sourceTwo);

    @Mappings({
        @Mapping(target = "first.value", expression = "java(\"expression\")"),
        @Mapping(target = "second.value", source = "sourceTwo")
    })
    Target twoArgsWithExpression(String sourceOne, String sourceTwo);
}
