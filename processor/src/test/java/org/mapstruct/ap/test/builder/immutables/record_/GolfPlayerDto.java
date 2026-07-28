/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.immutables.record_;

/**
 * Mutable source DTO mapped to the immutable {@link GolfPlayer} record.
 */
public class GolfPlayerDto {

    private String name;
    private Integer handicap;
    private int age;

    public GolfPlayerDto(String name, Integer handicap, int age) {
        this.name = name;
        this.handicap = handicap;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getHandicap() {
        return handicap;
    }

    public int getAge() {
        return age;
    }
}
