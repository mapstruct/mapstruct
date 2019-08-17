/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import java.util.List;
import java.util.Objects;

public class CarDto {

    private String name;
    private int year;
    private List<WheelDto> wheels;

    public CarDto() {
    }

    public CarDto(String name, int year, List<WheelDto> wheels) {
        this.name = name;
        this.year = year;
        this.wheels = wheels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<WheelDto> getWheels() {
        return wheels;
    }

    public void setWheels(List<WheelDto> wheels) {
        this.wheels = wheels;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        CarDto carDto = (CarDto) o;

        if ( year != carDto.year ) {
            return false;
        }
        if ( !Objects.equals( name, carDto.name ) ) {
            return false;
        }
        return Objects.equals( wheels, carDto.wheels );

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + year;
        result = 31 * result + ( wheels != null ? wheels.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString() {
        return "CarDto{" +
            "name='" + name + '\'' +
            ", year=" + year +
            ", wheels=" + wheels +
            '}';
    }

}
