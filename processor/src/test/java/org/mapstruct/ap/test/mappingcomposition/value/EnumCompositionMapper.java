/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcomposition.value;

import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;

/**
 * @author orange add
 */
@Mapper
public interface EnumCompositionMapper {

    @CustomValueAnnotation
    ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);

    @CustomValueAnnotation
    @ValueMapping( source = "STANDARD", target = "SPECIAL" )
    ExternalOrderType duplicateAnnotation(OrderType orderType);

    @CustomEnumAnnotation
    String orderTypeToString(OrderType orderType);
}
