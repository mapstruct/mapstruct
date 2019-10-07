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
 * @author Gunnar Morling
 */
@Mapper
public interface ErroneousOrderMapperUsingUnknownEnumConstants {

    ErroneousOrderMapperUsingUnknownEnumConstants INSTANCE = Mappers.getMapper(
        ErroneousOrderMapperUsingUnknownEnumConstants.class
    );

    @ValueMappings({
        @ValueMapping(source = "FOO", target = "SPECIAL"),
        @ValueMapping(source = "EXTRA", target = "BAR")
    })
    ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
}
