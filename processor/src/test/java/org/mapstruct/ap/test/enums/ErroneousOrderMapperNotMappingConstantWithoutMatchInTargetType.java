/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.enums;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Gunnar Morling
 */
@Mapper
public interface ErroneousOrderMapperNotMappingConstantWithoutMatchInTargetType {

    ErroneousOrderMapperNotMappingConstantWithoutMatchInTargetType INSTANCE = Mappers.getMapper(
        ErroneousOrderMapperNotMappingConstantWithoutMatchInTargetType.class
    );

    ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
}
