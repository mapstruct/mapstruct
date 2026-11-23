/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.value.enum2enum;

import org.jspecify.annotations.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;

/**
 * return is {@code @NonNull}, source is nullable, and an explicit {@code target = NULL} mapping on a regular
 * source constant yields a null return. Expected: hard ERROR — the generated implementation returns null for a
 * {@code @NonNull} declaration.
 */
@Mapper
public interface ErroneousNonNullReturnNullSourceMapper {

    @ValueMapping( source = "EXTRA", target = "SPECIAL" )
    @ValueMapping( source = "STANDARD", target = MappingConstants.NULL )
    @ValueMapping( source = "RETAIL", target = "RETAIL" )
    @ValueMapping( source = "B2B", target = "B2B" )
    @ValueMapping( source = MappingConstants.ANY_REMAINING, target = "DEFAULT" )
    @NonNull
    ExternalOrderType map(@NonNull OrderType orderType);

    @ValueMapping( source = "EXTRA", target = "SPECIAL" )
    @ValueMapping( source = "RETAIL", target = "RETAIL" )
    @ValueMapping( source = "B2B", target = "B2B" )
    @ValueMapping( source = MappingConstants.ANY_REMAINING, target = "DEFAULT" )
    @NonNull
    ExternalOrderType fromNullable(OrderType orderType);
}
