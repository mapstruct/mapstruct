/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.naming.spi;

/**
 * @author Gunnar Morling
 */
public class GolfPlayer {

    private double handicap;
    private String name;

    public double handicap() {
        return handicap;
    }

    public GolfPlayer withHandicap(double handicap) {
        this.handicap = handicap;
        return this;
    }

    public String name() {
        return name;
    }

    public GolfPlayer withName(String name) {
        this.name = name;
        return this;
    }
}
