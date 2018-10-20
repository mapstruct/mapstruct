/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.manysourcearguments;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;

@MapperConfig
public interface SourceTargetConfig {

    @Mapping(source = "address.houseNo", target = "houseNumber")
    @Mapping(source = "person.description", target = "description")
    DeliveryAddress personAndAddressToDeliveryAddress(Person person, Address address);

}
