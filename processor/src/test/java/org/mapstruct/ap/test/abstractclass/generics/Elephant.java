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
public class Elephant extends AbstractAnimal {
    private AnimalKey key;

    @Override
    public AnimalKey getKey() {
        return key;
    }

    @Override
    public void setKey(AnimalKey key) {
        this.key = key;
    }
}
