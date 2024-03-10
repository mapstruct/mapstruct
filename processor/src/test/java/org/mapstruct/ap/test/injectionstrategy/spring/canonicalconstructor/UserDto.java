/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.canonicalconstructor;

public class UserDto {

    private Integer userId;
    private String name;
    private String phoneNumber;

    public UserDto(Integer userId, String name, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
