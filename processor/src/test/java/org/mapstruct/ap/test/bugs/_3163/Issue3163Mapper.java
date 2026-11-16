/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3163;

import java.util.Optional;

import org.mapstruct.Mapper;

@Mapper
public interface Issue3163Mapper {

    Target map(Source value);

    Target.Nested map(Source.Nested value);

    default <T> Optional<T> wrapAsOptional(T value) {
        return Optional.ofNullable( value );
    }
}
