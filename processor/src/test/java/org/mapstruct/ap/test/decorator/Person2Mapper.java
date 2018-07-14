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

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { EmployerMapper.class } )
@DecoratedWith(Person2MapperDecorator.class)
public abstract class Person2Mapper {

    public static final Person2Mapper INSTANCE = Mappers.getMapper( Person2Mapper.class );

    public abstract PersonDto2 personToPersonDto(Person2 person);

    public abstract AddressDto addressToAddressDto(Address address);

    public SportsClub fromDto(SportsClubDto dto) {
        SportsClub sc = new SportsClub();
        sc.setName( dto.getName() );
        return sc;
    }

    public SportsClubDto toDto(SportsClub sc) {
        SportsClubDto dto = new SportsClubDto();
        dto.setName( sc.getName() );
        return dto;
    }
}
