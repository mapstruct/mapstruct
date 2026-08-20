/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4106.jdk17;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
@NullMarked
public interface Issue4106Mapper {

    Issue4106Mapper INSTANCE = Mappers.getMapper( Issue4106Mapper.class );

    TargetDto map(SourceEntity sourceEntity);

    default OffsetDateTime toOffsetTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant( ZoneOffset.UTC ).toOffsetDateTime();
    }

    record SourceEntity(ZonedDateTime createdAt, @Nullable ZonedDateTime closedAt) {
    }

    record TargetDto(OffsetDateTime createdAt, @Nullable OffsetDateTime closedAt) {
    }
}
