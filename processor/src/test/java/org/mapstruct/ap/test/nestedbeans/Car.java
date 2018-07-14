/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import java.util.List;

public class Car {

    private String name;
    private int year;
    private List<Wheel> wheels;

    public Car() {
    }

    public Car(String name, int year, List<Wheel> wheels) {
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

    public List<Wheel> getWheels() {
        return wheels;
    }

    public void setWheels(List<Wheel> wheels) {
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

        Car car = (Car) o;

        if ( year != car.year ) {
            return false;
        }
        if ( name != null ? !name.equals( car.name ) : car.name != null ) {
            return false;
        }
        return wheels != null ? wheels.equals( car.wheels ) : car.wheels == null;

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
        return "Car{" +
            "name='" + name + '\'' +
            ", year=" + year +
            ", wheels=" + wheels +
            '}';
    }

}
