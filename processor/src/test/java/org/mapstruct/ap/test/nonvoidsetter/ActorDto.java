/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nonvoidsetter;

public class ActorDto {

    private int oscars;
    private String name;

    public int getOscars() {
        return oscars;
    }

    public ActorDto setOscars(int oscars) {
        this.oscars = oscars;
        return this;
    }

    public String getName() {
        return name;
    }

    public ActorDto setName(String name) {
        this.name = name;
        return this;
    }
}
