/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.namesuggestion;

public class PersonDto {

    private String name;

    private int age;

    private GarageDto garage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public GarageDto getGarage() {
        return garage;
    }

    public void setGarage(GarageDto garage) {
        this.garage = garage;
    }
}
