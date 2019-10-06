/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.string2enum;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface ErroneousOrderMapperUsingNoAnyRemainingAndNoAnyUnmapped {

    ErroneousOrderMapperUsingNoAnyRemainingAndNoAnyUnmapped INSTANCE =
        Mappers.getMapper( ErroneousOrderMapperUsingNoAnyRemainingAndNoAnyUnmapped.class );

    @ValueMappings({
        @ValueMapping( source = MappingConstants.NULL, target = "DEFAULT" ),
        @ValueMapping( source = "STANDARD", target = MappingConstants.NULL )
    })
    OrderType map(String orderType);
}
