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

        String name = user.getName();
        if ( name != null ) {
            userDto.setName( name );
        }
        else {
            userDto.setName( null );
        }
        Car car = user.getCar();
        if ( car != null ) {
            if ( userDto.getCar() == null ) {
                userDto.setCar( new CarDto() );
            }
            carToCarDto( car, userDto.getCar() );
        }
        else {
            userDto.setCar( null );
        }
        Car secondCar = user.getSecondCar();
        if ( secondCar != null ) {
            if ( userDto.getSecondCar() == null ) {
                userDto.setSecondCar( new CarDto() );
            }
            carToCarDto( secondCar, userDto.getSecondCar() );
        }
        else {
            userDto.setSecondCar( null );
        }
        House house = user.getHouse();
        if ( house != null ) {
            if ( userDto.getHouse() == null ) {
                userDto.setHouse( new HouseDto() );
            }
            houseToHouseDto( house, userDto.getHouse() );
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

        List<WheelDto> list1 = new ArrayList<>( list.size() );
        for ( Wheel wheel : list ) {
            list1.add( wheelToWheelDto( wheel ) );
        }

        return list1;
    }

    protected void carToCarDto(Car car, CarDto mappingTarget) {
        if ( car == null ) {
            return;
        }

        String name = car.getName();
        if ( name != null ) {
            mappingTarget.setName( name );
        }
        else {
            mappingTarget.setName( null );
        }
        mappingTarget.setYear( car.getYear() );
        List<Wheel> wheels = car.getWheels();
        if ( mappingTarget.getWheels() != null ) {
            List<WheelDto> list = wheelListToWheelDtoList( wheels );
            if ( list != null ) {
                mappingTarget.getWheels().clear();
                mappingTarget.getWheels().addAll( list );
            }
        }
        else {
            List<WheelDto> list = wheelListToWheelDtoList( wheels );
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
        RoofType type = roof.getType();
        if ( type != null ) {
            mappingTarget.setType( roofTypeToExternalRoofType( type ) );
        }
        else {
            mappingTarget.setType( null );
        }
    }

    protected void houseToHouseDto(House house, HouseDto mappingTarget) {
        if ( house == null ) {
            return;
        }

        String name = house.getName();
        if ( name != null ) {
            mappingTarget.setName( name );
        }
        else {
            mappingTarget.setName( null );
        }
        mappingTarget.setYear( house.getYear() );
        Roof roof = house.getRoof();
        if ( roof != null ) {
            if ( mappingTarget.getRoof() == null ) {
                mappingTarget.setRoof( new RoofDto() );
            }
            roofToRoofDto( roof, mappingTarget.getRoof() );
        }
        else {
            mappingTarget.setRoof( null );
        }
    }
}
