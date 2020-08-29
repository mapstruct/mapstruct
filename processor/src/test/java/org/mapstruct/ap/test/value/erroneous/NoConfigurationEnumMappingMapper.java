/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.erroneous;

import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface NoConfigurationEnumMappingMapper {

    @EnumMapping(nameTransformationStrategy = MappingConstants.PREFIX_TRANSFORMATION)
    @ValueMapping(source = "EXTRA", target = "SPECIAL")
    @ValueMapping(source = "STANDARD", target = "DEFAULT")
    @ValueMapping(source = "NORMAL", target = "DEFAULT")
    ExternalOrderType onlyWithMappings(OrderType orderType);
}
