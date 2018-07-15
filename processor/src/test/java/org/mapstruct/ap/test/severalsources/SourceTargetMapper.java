/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.severalsources;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper( uses = ReferencedMapper.class )
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mappings({
        @Mapping(source = "address.houseNo", target = "houseNumber"),
        @Mapping(source = "person.description", target = "description")
    })
    DeliveryAddress personAndAddressToDeliveryAddress(Person person, Address address);

    @Mappings({
        @Mapping(source = "address.houseNo", target = "houseNumber"),
        @Mapping(source = "person.description", target = "description")
    })
    void personAndAddressToDeliveryAddress(Person person, Address address,
                                           @MappingTarget DeliveryAddress deliveryAddress);

    @Mapping( target = "description", source = "person.description")
    DeliveryAddress personAndAddressToDeliveryAddress(Person person, Integer houseNumber, int zipCode,
            String street);

}
