/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

public class ShelveDTO {

    private CoolBeerDTO coolBeer;

    public CoolBeerDTO getCoolBeer() {
        return coolBeer;
    }

    public void setCoolBeer(CoolBeerDTO coolBeer) {
        this.coolBeer = coolBeer;
    }
}
