/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics;

/**
 * @author Ben Zegveld
 */
public class GenericSourceContainer<T> {

    private String otherValue;

    public GenericSourceContainer(T contained, String otherValue) {
        this.contained = contained;
        this.otherValue = otherValue;
    }

    private T contained;

    public T getContained() {
        return contained;
    }

    public String getOtherValue() {
        return otherValue;
    }
}
