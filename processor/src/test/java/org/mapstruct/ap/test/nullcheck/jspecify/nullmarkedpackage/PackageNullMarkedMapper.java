/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.nullmarkedpackage;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Mapper in a @NullMarked package with NullValueCheckStrategy.ALWAYS.
 * Since the package is @NullMarked, unannotated types are effectively @NonNull
 * and no null checks are produced despite the ALWAYS strategy.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PackageNullMarkedMapper {

    PackageNullMarkedMapper INSTANCE = Mappers.getMapper( PackageNullMarkedMapper.class );

    PackageNullMarkedTargetBean map(PackageNullMarkedSourceBean source);
}
