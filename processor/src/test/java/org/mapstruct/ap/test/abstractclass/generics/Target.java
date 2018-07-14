/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.abstractclass.generics;

/**
 * @author Andreas Gudian
 *
 */
public class Target {
    private AnimalKey animalKey;
    private KeyOfAllBeings keyOfAllBeings;

    public AnimalKey getAnimalKey() {
        return animalKey;
    }

    public void setAnimalKey(AnimalKey animalKey) {
        this.animalKey = animalKey;
    }

    public KeyOfAllBeings getKeyOfAllBeings() {
        return keyOfAllBeings;
    }

    public void setKeyOfAllBeings(KeyOfAllBeings keyOfAllBeings) {
        this.keyOfAllBeings = keyOfAllBeings;
    }
}
