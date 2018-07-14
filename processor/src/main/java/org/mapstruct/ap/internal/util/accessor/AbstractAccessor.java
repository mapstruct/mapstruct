/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;

/**
 * This is an abstract implementation of an {@link Accessor} that provides the common implementation.
 *
 * @author Filip Hrisafov
 */
abstract class AbstractAccessor<T extends Element> implements Accessor {

    protected final T element;

    AbstractAccessor(T element) {
        this.element = element;
    }

    @Override
    public Name getSimpleName() {
        return element.getSimpleName();
    }

    @Override
    public Set<Modifier> getModifiers() {
        return element.getModifiers();
    }

    @Override
    public T getElement() {
        return element;
    }
}
