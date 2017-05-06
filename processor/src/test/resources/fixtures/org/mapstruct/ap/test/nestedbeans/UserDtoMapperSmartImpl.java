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
    date = "2017-05-04T00:00:47+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
public class UserDtoMapperSmartImpl implements UserDtoMapperSmart {

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
    public org.mapstruct.ap.test.nestedbeans.other.UserDto userToUserDto2(User user) {
        if ( user == null ) {
            return null;
        }

        org.mapstruct.ap.test.nestedbeans.other.UserDto userDto = new org.mapstruct.ap.test.nestedbeans.other.UserDto();

        userDto.setName( user.getName() );
        userDto.setCar( carToCarDto1( user.getCar() ) );
        userDto.setHouse( houseToHouseDto1( user.getHouse() ) );

        return userDto;
    }

    protected WheelDto wheelToWheelDto(Wheel wheel) {
        if ( wheel == null ) {
            return null;
        }

        WheelDto wheelDto = new WheelDto();

        wheelDto.setFront( wheel.isFront() );
        wheelDto.setRight( wheel.isRight() );

        return wheelDto;
    }

    protected List<WheelDto> wheelListToWheelDtoList(List<Wheel> list) {
        if ( list == null ) {
            return null;
        }

        List<WheelDto> list1 = new ArrayList<WheelDto>( list.size() );
        for ( Wheel wheel : list ) {
            list1.add( wheelToWheelDto( wheel ) );
        }

        return list1;
    }

    protected CarDto carToCarDto(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDto carDto = new CarDto();

        carDto.setName( car.getName() );
        carDto.setYear( car.getYear() );
        carDto.setWheels( wheelListToWheelDtoList( car.getWheels() ) );

        return carDto;
    }

    protected ExternalRoofType roofTypeToExternalRoofType(RoofType roofType) {
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

    protected RoofDto roofToRoofDto(Roof roof) {
        if ( roof == null ) {
            return null;
        }

        RoofDto roofDto = new RoofDto();

        roofDto.setColor( String.valueOf( roof.getColor() ) );
        roofDto.setType( roofTypeToExternalRoofType( roof.getType() ) );

        return roofDto;
    }

    protected HouseDto houseToHouseDto(House house) {
        if ( house == null ) {
            return null;
        }

        HouseDto houseDto = new HouseDto();

        houseDto.setName( house.getName() );
        houseDto.setYear( house.getYear() );
        houseDto.setRoof( roofToRoofDto( house.getRoof() ) );

        return houseDto;
    }

    protected org.mapstruct.ap.test.nestedbeans.other.WheelDto wheelToWheelDto1(Wheel wheel) {
        if ( wheel == null ) {
            return null;
        }

        org.mapstruct.ap.test.nestedbeans.other.WheelDto wheelDto = new org.mapstruct.ap.test.nestedbeans.other.WheelDto();

        wheelDto.setFront( wheel.isFront() );
        wheelDto.setRight( wheel.isRight() );

        return wheelDto;
    }

    protected List<org.mapstruct.ap.test.nestedbeans.other.WheelDto> wheelListToWheelDtoList1(List<Wheel> list) {
        if ( list == null ) {
            return null;
        }

        List<org.mapstruct.ap.test.nestedbeans.other.WheelDto> list1 = new ArrayList<org.mapstruct.ap.test.nestedbeans.other.WheelDto>( list.size() );
        for ( Wheel wheel : list ) {
            list1.add( wheelToWheelDto1( wheel ) );
        }

        return list1;
    }

    protected org.mapstruct.ap.test.nestedbeans.other.CarDto carToCarDto1(Car car) {
        if ( car == null ) {
            return null;
        }

        org.mapstruct.ap.test.nestedbeans.other.CarDto carDto = new org.mapstruct.ap.test.nestedbeans.other.CarDto();

        carDto.setName( car.getName() );
        carDto.setYear( car.getYear() );
        carDto.setWheels( wheelListToWheelDtoList1( car.getWheels() ) );

        return carDto;
    }

    protected org.mapstruct.ap.test.nestedbeans.other.RoofDto roofToRoofDto1(Roof roof) {
        if ( roof == null ) {
            return null;
        }

        org.mapstruct.ap.test.nestedbeans.other.RoofDto roofDto = new org.mapstruct.ap.test.nestedbeans.other.RoofDto();

        roofDto.setColor( String.valueOf( roof.getColor() ) );

        return roofDto;
    }

    protected org.mapstruct.ap.test.nestedbeans.other.HouseDto houseToHouseDto1(House house) {
        if ( house == null ) {
            return null;
        }

        org.mapstruct.ap.test.nestedbeans.other.HouseDto houseDto = new org.mapstruct.ap.test.nestedbeans.other.HouseDto();

        houseDto.setName( house.getName() );
        houseDto.setYear( house.getYear() );
        houseDto.setRoof( roofToRoofDto1( house.getRoof() ) );

        return houseDto;
    }
}
