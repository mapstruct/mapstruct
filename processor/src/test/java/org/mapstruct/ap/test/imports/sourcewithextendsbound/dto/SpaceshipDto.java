/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.sourcewithextendsbound.dto;

import java.util.Collection;

public class SpaceshipDto {

    private Collection<AstronautDto> astronauts;

    public Collection<AstronautDto> getAstronauts() {
        return astronauts;
    }

    public void setAstronauts(Collection<AstronautDto> astronauts) {
        this.astronauts = astronauts;
    }
}
