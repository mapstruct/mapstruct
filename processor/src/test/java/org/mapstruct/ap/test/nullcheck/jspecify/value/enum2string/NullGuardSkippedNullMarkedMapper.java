/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.value.enum2string;

import org.jspecify.annotations.NullMarked;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ap.test.value.OrderType;

@Mapper
@NullMarked
public interface NullGuardSkippedNullMarkedMapper {

    @ValueMapping( source = "EXTRA", target = "SPECIAL" )
    @ValueMapping( source = "STANDARD", target = "DEFAULT" )
    @ValueMapping( source = "NORMAL", target = "DEFAULT" )
    @ValueMapping( source = "RETAIL", target = "RETAIL" )
    @ValueMapping( source = "B2B", target = "B2B" )
    String map(OrderType orderType);
}
