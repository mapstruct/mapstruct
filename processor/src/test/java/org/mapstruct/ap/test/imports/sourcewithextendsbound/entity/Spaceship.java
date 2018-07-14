/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.sourcewithextendsbound.entity;

import java.util.Collection;

public class Spaceship {

    private Collection<? extends Astronaut> astronauts;

    public Collection<? extends Astronaut> getAstronauts() {
        return astronauts;
    }

    public void setAstronauts(Collection<? extends Astronaut> astronauts) {
        this.astronauts = astronauts;
    }
}
