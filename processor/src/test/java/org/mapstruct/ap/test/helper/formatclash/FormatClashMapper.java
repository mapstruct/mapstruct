/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.formatclash;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * The same user method is combined with a conversion that uses different formats. Each format needs its own
 * null-safe two-step helper, while the same format can reuse the helper.
 */
@Mapper
public interface FormatClashMapper {

    FormatClashMapper INSTANCE = Mappers.getMapper( FormatClashMapper.class );

    @Named("toNumber")
    default Integer toNumber(FormatClashSource.Value value) {
        return value == null ? null : value.getNumber();
    }

    @Named("toDate")
    default LocalDate toDate(FormatClashSource.Value value) {
        return value == null ? null : value.getDate();
    }

    @Mapping(target = "decimal", source = "value", qualifiedByName = "toNumber", numberFormat = "#.00")
    @Mapping(target = "grouped", source = "value", qualifiedByName = "toNumber", numberFormat = "#,###")
    @Mapping(target = "sameFormat", source = "value", qualifiedByName = "toNumber", numberFormat = "#.00")
    @Mapping(target = "isoDate", source = "value", qualifiedByName = "toDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "germanDate", source = "value", qualifiedByName = "toDate", dateFormat = "dd.MM.yyyy")
    FormatClashTarget map(FormatClashSource source);
}
