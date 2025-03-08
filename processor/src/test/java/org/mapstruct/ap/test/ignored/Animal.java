/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignored;

public class Animal {

    //CHECKSTYLE:OFF
    public Integer publicAge;
    public String publicColour;
    //CHECKSTYLE:OFN
    private String colour;
    private String name;
    private int size;
    private Integer age;

    // private String colour;
    public Animal() {
    }

    public Animal(String name, int size, Integer age, String colour) {
        this.name = name;
        this.size = size;
        this.publicAge = age;
        this.age = age;
        this.publicColour = colour;
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getColour() {
        return colour;
    }

    public void setColour( String colour ) {
        this.colour = colour;
    }

}
