/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
