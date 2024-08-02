/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics;

/**
 * @author Ben Zegveld
 */
public class GenericTargetContainer<T> {

    private String otherValue;
    private T contained;

    public T getContained() {
        return contained;
    }

    public void setContained(T contained) {
        this.contained = contained;
    }

    public String getOtherValue() {
        return otherValue;
    }

    public void setOtherValue(String otherValue) {
        this.otherValue = otherValue;
    }
}
