/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import java.util.Objects;

public class HouseDto {

    private String name;
    private int year;
    private RoofDto roof;

    public HouseDto() {
    }

    public HouseDto(String name, int year, RoofDto roof) {
        this.name = name;
        this.year = year;
        this.roof = roof;
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

    public RoofDto getRoof() {
        return roof;
    }

    public void setRoof(RoofDto roof) {
        this.roof = roof;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        HouseDto houseDto = (HouseDto) o;

        if ( year != houseDto.year ) {
            return false;
        }
        if ( !Objects.equals( name, houseDto.name ) ) {
            return false;
        }
        return Objects.equals( roof, houseDto.roof );

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + year;
        result = 31 * result + ( roof != null ? roof.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString() {
        return "HouseDto{" +
            "name='" + name + '\'' +
            ", year=" + year +
            ", roof=" + roof +
            '}';
    }

}
