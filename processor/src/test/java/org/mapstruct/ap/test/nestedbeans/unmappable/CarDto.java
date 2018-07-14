/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable;

import java.util.List;

public class CarDto {

    private String name;
    private int year;
    private List<WheelDto> wheels;

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

}
