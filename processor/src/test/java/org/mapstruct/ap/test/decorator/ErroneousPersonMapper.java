/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(ErroneousPersonMapperDecorator.class)
public interface ErroneousPersonMapper {

    ErroneousPersonMapper INSTANCE = Mappers.getMapper( ErroneousPersonMapper.class );

    PersonDto personToPersonDto(Person person);

    AddressDto addressToAddressDto(Address address);
}
