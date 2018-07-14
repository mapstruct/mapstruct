/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.severalsources;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousSourceTargetMapper {

    ErroneousSourceTargetMapper INSTANCE = Mappers.getMapper( ErroneousSourceTargetMapper.class );

    DeliveryAddress addressAndAddressToDeliveryAddress(Address address1, Address address2);
}
