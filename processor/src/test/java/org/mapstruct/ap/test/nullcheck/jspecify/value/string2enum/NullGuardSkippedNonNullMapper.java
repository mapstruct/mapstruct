/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.value.string2enum;

import org.jspecify.annotations.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ap.test.value.OrderType;

@Mapper
public interface NullGuardSkippedNonNullMapper {

    @ValueMapping( source = "SPECIAL", target = "EXTRA" )
    @ValueMapping( source = "DEFAULT", target = "STANDARD" )
    @ValueMapping( source = MappingConstants.ANY_REMAINING, target = "RETAIL" )
    @NonNull
    OrderType map(@NonNull String orderType);
}
