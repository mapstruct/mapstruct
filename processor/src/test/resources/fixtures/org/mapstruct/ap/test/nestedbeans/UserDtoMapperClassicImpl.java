/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.nestedbeans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-05-04T00:00:46+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
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

        List<WheelDto> list = new ArrayList<WheelDto>( wheels.size() );
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

        ExternalRoofType externalRoofType;

        switch ( roofType ) {
            case OPEN: externalRoofType = ExternalRoofType.OPEN;
            break;
            case BOX: externalRoofType = ExternalRoofType.BOX;
            break;
            case GAMBREL: externalRoofType = ExternalRoofType.GAMBREL;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + roofType );
        }

        return externalRoofType;
    }
}
