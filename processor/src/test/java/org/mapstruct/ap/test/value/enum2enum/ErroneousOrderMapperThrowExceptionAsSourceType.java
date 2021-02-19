/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.enum2enum;

import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.factory.Mappers;

/**
 * @author Jude Niroshan
 */
@Mapper
public interface ErroneousOrderMapperThrowExceptionAsSourceType {

    ErroneousOrderMapperThrowExceptionAsSourceType INSTANCE = Mappers.getMapper(
        ErroneousOrderMapperThrowExceptionAsSourceType.class );

    @ValueMappings({
        @ValueMapping(source = "EXTRA", target = "SPECIAL"),
        @ValueMapping(source = "STANDARD", target = "DEFAULT"),
        @ValueMapping(source = "NORMAL", target = "DEFAULT"),
        @ValueMapping(source = "<ANY_REMAINING>", target = "DEFAULT"),
        @ValueMapping(source = "<THROW_EXCEPTION>", target = "DEFAULT")
    })
    ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
}
