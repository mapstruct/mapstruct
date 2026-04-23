/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper with nested property chains and JSpecify annotations.
 * Tests that the deepest property's annotation is used for the null check decision.
 */
@Mapper
public interface JSpecifyNestedMapper {

    JSpecifyNestedMapper INSTANCE = Mappers.getMapper( JSpecifyNestedMapper.class );

    // nonNullAddress.street: leaf @NonNull -> target @NonNull -> skip null check (source @NonNull)
    @Mapping(target = "street", source = "nonNullAddress.street")
    // nonNullAddress.city: leaf @Nullable -> target @NonNull -> null check
    @Mapping(target = "city", source = "nonNullAddress.city")
    // nonNullAddress.street: leaf @NonNull -> target unannotated -> skip null check (source @NonNull)
    @Mapping(target = "nullableStreet", source = "nonNullAddress.street")
    FlatTargetBean map(NestedSourceBean source);
}
