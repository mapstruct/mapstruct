/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.shared;

/**
 * @author Kevin Grüneberg
 */
public class CustomerDto {

    private GenderDto gender;

    private String name;

    public GenderDto getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(GenderDto gender) {
        this.gender = gender;
    }
}
