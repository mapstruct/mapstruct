/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.exception;

import org.mapstruct.EnumMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ap.test.value.CustomIllegalArgumentException;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;

/**
 * @author Filip Hrisafov
 */
@Mapper(unexpectedValueMappingException = CustomIllegalArgumentException.class)
public interface CustomUnexpectedValueMappingExceptionDefinedInMapperAndEnumMapping {

    @ValueMapping(source = MappingConstants.ANY_UNMAPPED, target = "DEFAULT")
    ExternalOrderType withAnyUnmapped(OrderType orderType);

    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "DEFAULT")
    ExternalOrderType withAnyRemaining(OrderType orderType);

    @ValueMapping(source = "EXTRA", target = "SPECIAL")
    @ValueMapping(source = "STANDARD", target = "DEFAULT")
    @ValueMapping(source = "NORMAL", target = "DEFAULT")
    ExternalOrderType onlyWithMappings(OrderType orderType);

    // If unexpectedValueMappingException is explicitly defined then it should be used instead of what is in the SPI
    @EnumMapping(unexpectedValueMappingException = IllegalArgumentException.class)
    @InheritInverseConfiguration(name = "onlyWithMappings")
    OrderType inverseOnlyWithMappings(ExternalOrderType orderType);
}
