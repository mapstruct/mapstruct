/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._394.source;

import java.util.Map;

public class Cars {
    private Map<String, AnotherCar> makeToCar;

    public Map<String, AnotherCar> getMakeToCar() {
        return makeToCar;
    }

    public void setMakeToCar(Map<String, AnotherCar> makeToCar) {
        this.makeToCar = makeToCar;
    }
}
