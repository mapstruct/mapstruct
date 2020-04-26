/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * An {@link Accessor} that wraps a {@link VariableElement}.
 *
 * @author Filip Hrisafov
 */
public class ParameterElementAccessor extends AbstractAccessor<Element> {

    protected final String name;

    public ParameterElementAccessor(Element element, String name) {
        super( element );
        this.name = name;
    }

    @Override
    public String getSimpleName() {
        return name != null ? name : super.getSimpleName();
    }

    @Override
    public TypeMirror getAccessedType() {
        return element.asType();
    }

    @Override
    public String toString() {
        return element.toString();
    }

    @Override
    public AccessorType getAccessorType() {
        return AccessorType.PARAMETER;
    }

}
