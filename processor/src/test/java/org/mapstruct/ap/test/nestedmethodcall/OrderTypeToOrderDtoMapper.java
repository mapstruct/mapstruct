/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedmethodcall;

import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface OrderTypeToOrderDtoMapper {

    OrderTypeToOrderDtoMapper INSTANCE = Mappers.getMapper( OrderTypeToOrderDtoMapper.class );

    OrderDto sourceToTarget(OrderType source);

    OrderDetailsDto detailsToDto(OrderDetailsType source);

    @IterableMapping(dateFormat = "dd.MM.yyyy")
    List<String> stringListToDateList(List<JAXBElement<XMLGregorianCalendar>> dates);
}
