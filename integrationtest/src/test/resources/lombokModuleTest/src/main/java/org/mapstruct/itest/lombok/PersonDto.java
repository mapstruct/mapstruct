/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.lombok;

public class PersonDto {
    private final String name;
    private final int age;
    private final AddressDto address;

    public PersonDto(String name, int age, AddressDto address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public AddressDto getAddress() {
        return address;
    }
}
