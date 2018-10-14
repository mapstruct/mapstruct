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
 * @author Sjaak Derksen
 */
@Mapper
public interface ErroneousOrderMapperDuplicateANY {

    ErroneousOrderMapperDuplicateANY INSTANCE = Mappers.getMapper( ErroneousOrderMapperDuplicateANY.class );

    @ValueMapping(source = "EXTRA", target = "SPECIAL")
    @ValueMapping(source = "STANDARD", target = "DEFAULT")
    @ValueMapping(source = "NORMAL", target = "DEFAULT")
    @ValueMapping( source = "<ANY_REMAINING>", target = "DEFAULT" )
    @ValueMapping( source = "<ANY_UNMAPPED>", target = "DEFAULT" )
    ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
}
