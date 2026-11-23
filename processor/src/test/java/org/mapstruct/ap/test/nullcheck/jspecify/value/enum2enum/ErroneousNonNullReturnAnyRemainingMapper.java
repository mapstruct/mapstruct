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
 * return is {@code @NonNull} and the {@code ANY_REMAINING} catch-all maps to null. Expected: hard ERROR —
 * the generated default branch returns null for a {@code @NonNull} declaration.
 */
@Mapper
public interface ErroneousNonNullReturnAnyRemainingMapper {

    @ValueMapping( source = "EXTRA", target = "SPECIAL" )
    @ValueMapping( source = "STANDARD", target = "DEFAULT" )
    @ValueMapping( source = "RETAIL", target = "RETAIL" )
    @ValueMapping( source = "B2B", target = "B2B" )
    @ValueMapping( source = MappingConstants.ANY_REMAINING, target = MappingConstants.NULL )
    @NonNull
    ExternalOrderType map(@NonNull OrderType orderType);
}
