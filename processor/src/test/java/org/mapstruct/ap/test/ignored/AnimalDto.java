/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignored;

public class AnimalDto {

    //CHECKSTYLE:OFF
    public Integer publicAge;
    public String publicColor;
    //CHECKSTYLE:ON
    private String name;
    private Integer size;
    private Integer age;
    private String color;

    public AnimalDto() {

    }

    public AnimalDto(String name, Integer size, Integer age, String color) {
        this.name = name;
        this.size = size;
        this.publicAge = age;
        this.age = age;
        this.publicColor = color;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
