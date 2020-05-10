/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.optional;

import java.util.Optional;

import org.mapstruct.Mapper;

@Mapper
public interface OptionalMapper {

    default <T> T fromOptional(final Optional<T> optional) {
        return optional.orElse( null );
    }

    default <T> Optional<T> asOptional(final T optional) {
        return optional != null ? Optional.of( optional ) : Optional.empty();
    }
}
