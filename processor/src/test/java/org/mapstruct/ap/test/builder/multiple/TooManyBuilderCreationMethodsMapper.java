/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.multiple;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.builder.multiple.builder.Case;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface TooManyBuilderCreationMethodsMapper {

    TooManyBuilderCreationMethodsMapper INSTANCE = Mappers.getMapper( TooManyBuilderCreationMethodsMapper.class );

    Case map(Source source);
}
