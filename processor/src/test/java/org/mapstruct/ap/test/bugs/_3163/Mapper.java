/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3163;

import java.util.Optional;

import org.mapstruct.Mapping;

@org.mapstruct.Mapper(config = MapStructConfiguration.class)
public interface Mapper {
    @Mapping(target = "from", ignore = true)
    ImmutableATarget map(ImmutableASource value);

    @Mapping(target = "from", ignore = true)
    ImmutableBTarget map(ImmutableBSource value);

    default <T> Optional<T> wrapAsOptional(T value) {
        return Optional.ofNullable( value );
    }
}
