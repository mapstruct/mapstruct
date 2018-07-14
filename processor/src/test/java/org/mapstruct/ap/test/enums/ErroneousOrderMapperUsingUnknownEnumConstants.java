/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.enums;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Gunnar Morling
 */
@Mapper
public interface ErroneousOrderMapperUsingUnknownEnumConstants {

    ErroneousOrderMapperUsingUnknownEnumConstants INSTANCE = Mappers.getMapper(
        ErroneousOrderMapperUsingUnknownEnumConstants.class
    );

    @Mappings({
        @Mapping(source = "FOO", target = "SPECIAL"),
        @Mapping(source = "EXTRA", target = "BAR")
    })
    ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
}
