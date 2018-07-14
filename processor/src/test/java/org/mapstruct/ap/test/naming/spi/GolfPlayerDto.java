/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.naming.spi;

/**
 * @author Gunnar Morling
 */
public class GolfPlayerDto {

    private double handicap;
    private String name;

    public double handicap() {
        return handicap;
    }

    public void withHandicap(double handicap) {
        this.handicap = handicap;
    }

    public String name() {
        return name;
    }

    public void withName(String name) {
        this.name = name;
    }
}
