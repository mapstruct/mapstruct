/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3704;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue3704OverloadedSetterMapper {

    Issue3704OverloadedSetterMapper INSTANCE = Mappers.getMapper( Issue3704OverloadedSetterMapper.class );

    OverloadedTarget map(Source source);

    default <T> Optional<T> wrapOptional(T value) {
        return Optional.ofNullable( value );
    }
}
