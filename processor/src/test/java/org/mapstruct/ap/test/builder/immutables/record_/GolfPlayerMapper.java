/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.immutables.record_;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Mapper exercising {@link org.mapstruct.ap.spi.ImmutablesBuilderProvider} record-builder detection:
 * MapStruct should discover {@link GolfPlayerBuilder} and use it instead of the record constructor.
 * <p>
 * {@code nullValueCheckStrategy = ALWAYS} ensures MapStruct skips the builder setter when a source value is
 * {@code null}, so the builder's mandatory-field check fires at {@code build()} time for unset required fields.
 */
@Mapper( nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS )
public interface GolfPlayerMapper {

    GolfPlayerMapper INSTANCE = Mappers.getMapper( GolfPlayerMapper.class );

    GolfPlayer toGolfPlayer(GolfPlayerDto dto);
}
