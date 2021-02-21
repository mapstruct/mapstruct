package org.mapstruct.ap.test.value.enum2enum;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderThrowExceptionMapper {
    OrderThrowExceptionMapper INSTANCE = Mappers.getMapper( OrderThrowExceptionMapper.class );


    OrderDto orderEntityToDto(OrderEntity order);

    @ValueMappings({
        @ValueMapping(source = "EXTRA", target = "SPECIAL"),
        @ValueMapping(source = "STANDARD", target = "DEFAULT"),
        @ValueMapping(source = "NORMAL", target = MappingConstants.THROW_EXCEPTION)
    })
    ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);

    @InheritInverseConfiguration
    OrderType externalOrderTypeToOrderType(ExternalOrderType orderType);
}
