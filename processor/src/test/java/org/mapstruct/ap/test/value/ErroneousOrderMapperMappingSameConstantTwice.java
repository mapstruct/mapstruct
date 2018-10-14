/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value;

import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Gunnar Morling
 */
@Mapper
public interface ErroneousOrderMapperMappingSameConstantTwice {

    ErroneousOrderMapperMappingSameConstantTwice INSTANCE = Mappers.getMapper(
        ErroneousOrderMapperMappingSameConstantTwice.class
    );

    @ValueMapping(source = "EXTRA", target = "SPECIAL")
    @ValueMapping(source = "EXTRA", target = "DEFAULT")
    @ValueMapping(source = "STANDARD", target = "DEFAULT")
    @ValueMapping(source = "NORMAL", target = "DEFAULT")
    ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
}
