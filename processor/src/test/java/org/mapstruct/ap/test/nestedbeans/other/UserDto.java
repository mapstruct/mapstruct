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
package org.mapstruct.ap.test.nestedbeans.other;

public class UserDto {

    private String name;
    private CarDto car;
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
