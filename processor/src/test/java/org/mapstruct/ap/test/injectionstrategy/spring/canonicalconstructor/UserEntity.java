package org.mapstruct.ap.test.injectionstrategy.spring.canonicalconstructor;

public class UserEntity {

    private Integer userId;
    private String name;

    public UserEntity(Integer userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

}
