/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.shared;

public class UserEntity {

    private Integer userId;
    private String name;
    private AddressEntity address;

    public UserEntity(Integer userId, String name, AddressEntity address) {
        this.userId = userId;
        this.name = name;
        this.address = address;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public AddressEntity getAddress() {
        return address;
    }
}
