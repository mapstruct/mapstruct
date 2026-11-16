/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.iterabletoset;

import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Xiu-Hong Kooi
 */
public class FruitsMenu implements Iterable<Fruit> {

    private Set<Fruit> fruits;

    public FruitsMenu(Set<Fruit> fruits) {
        this.fruits = fruits;
    }

    public Set<Fruit> getFruits() {
        return fruits;
    }

    public void setFruits(Set<Fruit> fruits) {
        this.fruits = fruits;
    }

    @Override
    public Iterator<Fruit> iterator() {
        return this.fruits.iterator();
    }
}
