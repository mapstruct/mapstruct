/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

public class Wrapper<T> {

    public Wrapper() {
    }

    public Wrapper(T wrapped) {
        this.wrapped = wrapped;
    }

    private T wrapped;

    public T getWrapped() {
        return wrapped;
    }

    public void setWrapped(T wrapped) {
        this.wrapped = wrapped;
    }
}
