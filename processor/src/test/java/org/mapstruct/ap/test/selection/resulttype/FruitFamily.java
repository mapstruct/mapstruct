/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.resulttype;

/**
 * @author Filip Hrisafov
 */
public class FruitFamily {

    private IsFruit fruit;

    public IsFruit getFruit() {
        return fruit;
    }

    public void setFruit(IsFruit fruit) {
        this.fruit = fruit;
    }
}
