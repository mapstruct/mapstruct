/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

/**
 * Testing Multiple Bounds
 * @author sjaak
 */
public class WildCardExtendsMBWrapper<T> {

    private T wrapped;

    public WildCardExtendsMBWrapper(T wrapped) {
        this.wrapped = wrapped;
    }

    public T getWrapped() {
        return wrapped;
    }

    public void setWrapped(T wrapped) {
        this.wrapped = wrapped;
    }
}
