/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2795.mapper;

import java.util.Optional;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;

@Mapper
public abstract class MapperConfig {

    <T> T unwrap(Optional<T> jsonNullable) {
        return jsonNullable.orElse( null );
    }

    <T> Optional<T> wrap(T object) {
        return Optional.of( object );
    }

    @Condition
    <T> boolean isNotEmpty(Optional<T> field) {
        return field != null && field.isPresent();
    }

}
