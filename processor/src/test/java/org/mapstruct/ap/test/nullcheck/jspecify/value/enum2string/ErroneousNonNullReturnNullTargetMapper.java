/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.value.enum2string;

import org.jspecify.annotations.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ap.test.value.OrderType;

@Mapper
public interface ErroneousNonNullReturnNullTargetMapper {

    @ValueMapping( source = "STANDARD", target = MappingConstants.NULL )
    @ValueMapping( source = "EXTRA", target = "SPECIAL" )
    @ValueMapping( source = "RETAIL", target = "RETAIL" )
    @ValueMapping( source = "B2B", target = "B2B" )
    @ValueMapping( source = "NORMAL", target = "NORMAL" )
    @NonNull
    String map(@NonNull OrderType orderType);

    @ValueMapping( source = "EXTRA", target = "SPECIAL" )
    @ValueMapping( source = "RETAIL", target = "RETAIL" )
    @ValueMapping( source = "B2B", target = "B2B" )
    @ValueMapping( source = "NORMAL", target = "NORMAL" )
    @NonNull
    String fromNullable(OrderType orderType);
}
