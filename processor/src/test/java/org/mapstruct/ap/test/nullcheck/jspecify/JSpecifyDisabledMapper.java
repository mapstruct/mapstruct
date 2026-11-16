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
 * Dedicated mapper for {@link JSpecifyDisabledTest} so that the test's JSpecify-disabled
 * compilation result is isolated from tests that share {@link JSpecifyNullCheckMapper} and
 * would otherwise keep the JSpecify-enabled {@code INSTANCE} cached in the JVM.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JSpecifyDisabledMapper {

    JSpecifyDisabledMapper INSTANCE = Mappers.getMapper( JSpecifyDisabledMapper.class );

    @Mapping(target = "nonNullTargetFromNullable", source = "nullableValue")
    TargetBean map(SourceBean source);
}
