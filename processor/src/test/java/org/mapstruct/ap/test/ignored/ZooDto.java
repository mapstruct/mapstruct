/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignored;

public class ZooDto {

    private AnimalDto animal;

    private String name;

    private String address;

    public ZooDto() {
    }

    public ZooDto(AnimalDto animal, String name, String address) {
        this.animal = animal;
        this.name = name;
        this.address = address;
    }

    public AnimalDto getAnimal() {
        return animal;
    }

    public void setAnimal(AnimalDto animal) {
        this.animal = animal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
