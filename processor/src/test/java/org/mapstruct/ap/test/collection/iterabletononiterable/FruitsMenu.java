/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.iterabletononiterable;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Saheb Preet Singh
 */
public class FruitsMenu implements Iterable<Fruit> {

    private List<Fruit> fruits;

    public FruitsMenu(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    public List<Fruit> getFruits() {
        return fruits;
    }

    public void setFruits(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    @Override
    public Iterator<Fruit> iterator() {
        return this.fruits.iterator();
    }
}
