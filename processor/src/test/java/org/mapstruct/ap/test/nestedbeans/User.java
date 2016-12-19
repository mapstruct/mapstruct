/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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

public class User {

    private String name;
    private Car car;
    private House house;

    public User() {
    }

    public User(String name, Car car, House house) {
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
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

        User user = (User) o;

        if ( name != null ? !name.equals( user.name ) : user.name != null ) {
            return false;
        }
        if ( car != null ? !car.equals( user.car ) : user.car != null ) {
            return false;
        }
        return house != null ? house.equals( user.house ) : user.house == null;

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
        return "User{" +
            "name='" + name + '\'' +
            ", car=" + car +
            ", house=" + house +
            '}';
    }

}
