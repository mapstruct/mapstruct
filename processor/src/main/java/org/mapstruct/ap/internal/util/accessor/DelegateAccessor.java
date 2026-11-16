/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

/**
 * An {@link Accessor} which delegates all calls to another {@link Accessor}.
 *
 * @author Filip Hrisafov
 */
public abstract class DelegateAccessor implements Accessor {

    protected final Accessor delegate;

    protected DelegateAccessor(Accessor delegate) {
        this.delegate = delegate;
    }

    @Override
    public TypeMirror getAccessedType() {
        return delegate.getAccessedType();
    }

    @Override
    public String getSimpleName() {
        return delegate.getSimpleName();
    }

    @Override
    public Set<Modifier> getModifiers() {
        return delegate.getModifiers();
    }

    @Override
    public Element getElement() {
        return delegate.getElement();
    }

    @Override
    public AccessorType getAccessorType() {
        return delegate.getAccessorType();
    }
}
