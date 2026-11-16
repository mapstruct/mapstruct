/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.iterabletolist;

import java.util.List;

/**
 *
 * @author Xiu-Hong Kooi
 */
public class FruitSalad {

    private Iterable<Fruit> fruits;

    public FruitSalad(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    public Iterable<Fruit> getFruits() {
        return fruits;
    }

    public void setFruits(List<Fruit> fruits) {
        this.fruits = fruits;
    }
}
