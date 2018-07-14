/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

public class House {

    private String name;
    private int year;
    private Roof roof;

    public House() {
    }

    public House(String name, int year, Roof roof) {


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

    public Roof getRoof() {
        return roof;
    }

    public void setRoof(Roof roof) {
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

        House house = (House) o;

        if ( year != house.year ) {
            return false;
        }
        if ( name != null ? !name.equals( house.name ) : house.name != null ) {
            return false;
        }
        return roof != null ? roof.equals( house.roof ) : house.roof == null;

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
        return "House{" +
            "name='" + name + '\'' +
            ", year=" + year +
            ", roof=" + roof +
            '}';
    }

}
