/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1566;

/**
 * @author Filip Hrisafov
 */
public abstract class AbstractBuilder<T extends AbstractBuilder<T>> {
    String id;

    @SuppressWarnings("unchecked")
    public T id(final String id) {
        this.id = id;
        return (T) this;
    }
}
