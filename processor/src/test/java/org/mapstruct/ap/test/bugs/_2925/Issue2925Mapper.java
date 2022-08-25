/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2925;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue2925Mapper {

    Issue2925Mapper INSTANCE = Mappers.getMapper( Issue2925Mapper.class );

    Target map(Source source);

    static <T> Optional<T> toOptional(T value) {
        return Optional.ofNullable( value );
    }
}
