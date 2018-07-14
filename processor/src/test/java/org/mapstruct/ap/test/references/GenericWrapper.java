/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references;

/**
 * @author Andreas Gudian
 *
 */
public class GenericWrapper<T> extends BaseType {
    private final T wrapped;

    public GenericWrapper(T someType) {
        this.wrapped = someType;
    }

    public T getWrapped() {
        return wrapped;
    }
}
