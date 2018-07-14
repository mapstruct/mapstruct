/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nonvoidsetter;

public class Actor {

    private int oscars;
    private String name;

    public Actor(int oscars, String name) {
        this.oscars = oscars;
        this.name = name;
    }

    public int getOscars() {
        return oscars;
    }

    public void setOscars(int oscars) {
        this.oscars = oscars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
