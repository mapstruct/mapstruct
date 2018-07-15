/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

public class UserDto {

    private String name;
    private CarDto car;
    private CarDto secondCar;
    private HouseDto house;

    public UserDto() {
    }

    public UserDto(String name, CarDto car, HouseDto house) {
        this.name = name;
        this.car = car;
        this.house = house;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }

    public CarDto getSecondCar() {
        return secondCar;
    }

    public void setSecondCar(CarDto secondCar) {
        this.secondCar = secondCar;
    }

    public HouseDto getHouse() {
        return house;
    }

    public void setHouse(HouseDto house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        UserDto userDto = (UserDto) o;

        if ( name != null ? !name.equals( userDto.name ) : userDto.name != null ) {
            return false;
        }
        if ( car != null ? !car.equals( userDto.car ) : userDto.car != null ) {
            return false;
        }
        return house != null ? house.equals( userDto.house ) : userDto.house == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + ( car != null ? car.hashCode() : 0 );
        result = 31 * result + ( house != null ? house.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
            "name='" + name + '\'' +
            ", car=" + car +
            ", house=" + house +
            '}';
    }

}
