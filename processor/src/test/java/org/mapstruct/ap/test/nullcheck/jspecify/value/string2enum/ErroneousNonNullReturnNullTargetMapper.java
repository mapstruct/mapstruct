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
public interface ErroneousNonNullReturnNullTargetMapper {

    @ValueMapping( source = "DEFAULT", target = MappingConstants.NULL )
    @ValueMapping( source = "SPECIAL", target = "EXTRA" )
    @ValueMapping( source = MappingConstants.ANY_REMAINING, target = "STANDARD" )
    @NonNull
    OrderType map(@NonNull String orderType);

    @ValueMapping( source = "SPECIAL", target = "EXTRA" )
    @ValueMapping( source = MappingConstants.ANY_REMAINING, target = "STANDARD" )
    @NonNull
    OrderType mapFromNullable(String orderType);
}
