/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDtoMapperClassic {

    UserDtoMapperClassic INSTANCE = Mappers.getMapper( UserDtoMapperClassic.class );

    UserDto userToUserDto(User user);

    CarDto carToCarDto(Car car);

    HouseDto houseToHouseDto(House house);

    RoofDto roofToRoofDto(Roof roof);

    List<WheelDto> mapWheels(List<Wheel> wheels);

    WheelDto mapWheel(Wheel wheels);

    ExternalRoofType mapRoofType(RoofType roofType);

}
