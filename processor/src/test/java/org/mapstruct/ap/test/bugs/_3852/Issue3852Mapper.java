/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3852;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author Dennis Melzer
 */
@Mapper(
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface Issue3852Mapper {

    Issue3852Mapper INSTANCE = Mappers.getMapper( Issue3852Mapper.class );

    Target map(Source source);
}
