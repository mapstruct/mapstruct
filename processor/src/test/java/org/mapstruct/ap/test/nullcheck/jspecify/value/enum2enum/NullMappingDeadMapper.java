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
 * source is {@code @NonNull} AND an explicit {@code source = NULL} mapping is present. Because the source
 * will never be null, the {@code NULL} mapping is dead code. Expected: WARNING, compilation succeeds.
 */
@Mapper
public interface NullMappingDeadMapper {

    @ValueMapping( source = MappingConstants.NULL, target = "DEFAULT" )
    @ValueMapping( source = "EXTRA", target = "SPECIAL" )
    @ValueMapping( source = "STANDARD", target = "DEFAULT" )
    @ValueMapping( source = "NORMAL", target = "DEFAULT" )
    @ValueMapping( source = "RETAIL", target = "RETAIL" )
    @ValueMapping( source = "B2B", target = "B2B" )
    ExternalOrderType map(@NonNull OrderType orderType);
}
