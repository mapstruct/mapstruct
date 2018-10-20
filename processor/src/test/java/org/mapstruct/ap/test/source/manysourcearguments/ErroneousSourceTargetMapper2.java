/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.manysourcearguments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ErroneousSourceTargetMapper2 {

    @Mapping( target = "houseNumber", source = "houseNo")
    DeliveryAddress addressAndAddressToDeliveryAddress(Address address, Person person);
}
