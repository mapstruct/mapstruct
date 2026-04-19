/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Nested source chain where the intermediate accessor is {@code @Nullable} but the deepest
 * accessor is {@code @NonNull}. The target setter is {@code @NonNull} — when the intermediate
 * returns null, the setter must not be invoked.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JSpecifyNullableIntermediateMapper {

    JSpecifyNullableIntermediateMapper INSTANCE = Mappers.getMapper( JSpecifyNullableIntermediateMapper.class );

    // nullableAddress is @Nullable; street is @NonNull; target setStreet is @NonNull.
    // Because the intermediate can be null, the null check must not be skipped.
    @Mapping(target = "street", source = "nullableAddress.street")
    FlatTargetBean map(NestedSourceBean source);
}
