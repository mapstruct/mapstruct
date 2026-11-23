/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.value.enum2enum;

import org.jspecify.annotations.NullMarked;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;

/**
 * method is {@code @NullMarked}; MapStruct should skip the generated null guard. No {@code NULL} mapping is present.
 */
@Mapper
@NullMarked
public interface NullGuardSkippedNullMarkedMapper {

    @ValueMapping( source = "EXTRA", target = "SPECIAL" )
    @ValueMapping( source = "STANDARD", target = "DEFAULT" )
    @ValueMapping( source = "NORMAL", target = "DEFAULT" )
    @ValueMapping( source = "RETAIL", target = "RETAIL" )
    @ValueMapping( source = "B2B", target = "B2B" )
    ExternalOrderType map(OrderType orderType);
}
