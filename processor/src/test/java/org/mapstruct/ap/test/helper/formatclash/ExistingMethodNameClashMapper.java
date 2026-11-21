/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.formatclash;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * The mapper already declares a method with the name that the null-safe two-step helper would get
 * ({@code toNumberToString}), so the helper needs a different name.
 */
@Mapper
public interface ExistingMethodNameClashMapper {

    ExistingMethodNameClashMapper INSTANCE = Mappers.getMapper( ExistingMethodNameClashMapper.class );

    default Integer toNumber(FormatClashSource.Value value) {
        return value == null ? null : value.getNumber();
    }

    default void toNumberToString(FormatClashSource.Value value) {
    }

    @Mapping(target = "decimal", source = "value", numberFormat = "#.00")
    @Mapping(target = "grouped", ignore = true)
    @Mapping(target = "sameFormat", ignore = true)
    @Mapping(target = "isoDate", ignore = true)
    @Mapping(target = "germanDate", ignore = true)
    FormatClashTarget map(FormatClashSource source);
}
