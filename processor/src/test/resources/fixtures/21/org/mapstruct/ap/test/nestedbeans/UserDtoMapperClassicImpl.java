/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-12T22:15:18+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
public class UserDtoMapperClassicImpl implements UserDtoMapperClassic {

    @Override
    public UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setName( user.getName() );
        userDto.setCar( carToCarDto( user.getCar() ) );
        userDto.setSecondCar( carToCarDto( user.getSecondCar() ) );
        userDto.setHouse( houseToHouseDto( user.getHouse() ) );

        return userDto;
    }

    @Override
    public CarDto carToCarDto(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDto carDto = new CarDto();

        carDto.setName( car.getName() );
        carDto.setYear( car.getYear() );
        carDto.setWheels( mapWheels( car.getWheels() ) );

        return carDto;
    }

    @Override
    public HouseDto houseToHouseDto(House house) {
        if ( house == null ) {
            return null;
        }

        HouseDto houseDto = new HouseDto();

        houseDto.setName( house.getName() );
        houseDto.setYear( house.getYear() );
        houseDto.setRoof( roofToRoofDto( house.getRoof() ) );

        return houseDto;
    }

    @Override
    public RoofDto roofToRoofDto(Roof roof) {
        if ( roof == null ) {
            return null;
        }

        RoofDto roofDto = new RoofDto();

        roofDto.setColor( String.valueOf( roof.getColor() ) );
        roofDto.setType( mapRoofType( roof.getType() ) );

        return roofDto;
    }

    @Override
    public List<WheelDto> mapWheels(List<Wheel> wheels) {
        if ( wheels == null ) {
            return null;
        }

        List<WheelDto> list = new ArrayList<>( wheels.size() );
        for ( Wheel wheel : wheels ) {
            list.add( mapWheel( wheel ) );
        }

        return list;
    }

    @Override
    public WheelDto mapWheel(Wheel wheels) {
        if ( wheels == null ) {
            return null;
        }

        WheelDto wheelDto = new WheelDto();

        wheelDto.setFront( wheels.isFront() );
        wheelDto.setRight( wheels.isRight() );

        return wheelDto;
    }

    @Override
    public ExternalRoofType mapRoofType(RoofType roofType) {
        if ( roofType == null ) {
            return null;
        }

        ExternalRoofType externalRoofType = switch ( roofType ) {
            case OPEN -> ExternalRoofType.OPEN;
            case BOX -> ExternalRoofType.BOX;
            case GAMBREL -> ExternalRoofType.GAMBREL;
        };

        return externalRoofType;
    }
}
