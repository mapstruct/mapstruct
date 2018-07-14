/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable;

public class HouseDto {

    private String name;
    private int year;
    private RoofDto roof;

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

    public RoofDto getRoof() {
        return roof;
    }

    public void setRoof(RoofDto roof) {
        this.roof = roof;
    }

}
