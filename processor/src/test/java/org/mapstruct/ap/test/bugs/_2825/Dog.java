/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2825;

/**
 * @author orange add
 */
public class Dog extends Animal {
    private String race;

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }
}
