/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-05-04T00:00:45+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
public class UserDtoUpdateMapperSmartImpl implements UserDtoUpdateMapperSmart {

    @Override
    public void userToUserDto(UserDto userDto, User user) {
        if ( user == null ) {
            return;
        }

        if ( user.getName() != null ) {
            userDto.setName( user.getName() );
        }
        if ( user.getCar() != null ) {
            if ( userDto.getCar() == null ) {
                userDto.setCar( new CarDto() );
            }
            carToCarDto( user.getCar(), userDto.getCar() );
        }
        else {
            userDto.setCar( null );
        }
        if ( user.getSecondCar() != null ) {
            if ( userDto.getSecondCar() == null ) {
                userDto.setSecondCar( new CarDto() );
            }
            carToCarDto( user.getSecondCar(), userDto.getSecondCar() );
        }
        else {
            userDto.setSecondCar( null );
        }
        if ( user.getHouse() != null ) {
            if ( userDto.getHouse() == null ) {
                userDto.setHouse( new HouseDto() );
            }
            houseToHouseDto( user.getHouse(), userDto.getHouse() );
        }
        else {
            userDto.setHouse( null );
        }
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

    protected void carToCarDto(Car car, CarDto mappingTarget) {
        if ( car == null ) {
            return;
        }

        if ( car.getName() != null ) {
            mappingTarget.setName( car.getName() );
        }
        mappingTarget.setYear( car.getYear() );
        if ( mappingTarget.getWheels() != null ) {
            List<WheelDto> list = wheelListToWheelDtoList( car.getWheels() );
            if ( list != null ) {
                mappingTarget.getWheels().clear();
                mappingTarget.getWheels().addAll( list );
            }
        }
        else {
            List<WheelDto> list = wheelListToWheelDtoList( car.getWheels() );
            if ( list != null ) {
                mappingTarget.setWheels( list );
            }
        }
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

    protected void roofToRoofDto(Roof roof, RoofDto mappingTarget) {
        if ( roof == null ) {
            return;
        }

        mappingTarget.setColor( String.valueOf( roof.getColor() ) );
        if ( roof.getType() != null ) {
            mappingTarget.setType( roofTypeToExternalRoofType( roof.getType() ) );
        }
    }

    protected void houseToHouseDto(House house, HouseDto mappingTarget) {
        if ( house == null ) {
            return;
        }

        if ( house.getName() != null ) {
            mappingTarget.setName( house.getName() );
        }
        mappingTarget.setYear( house.getYear() );
        if ( house.getRoof() != null ) {
            if ( mappingTarget.getRoof() == null ) {
                mappingTarget.setRoof( new RoofDto() );
            }
            roofToRoofDto( house.getRoof(), mappingTarget.getRoof() );
        }
        else {
            mappingTarget.setRoof( null );
        }
    }
}
