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
public class ElementAccessor extends AbstractAccessor<Element> {

    private final AccessorType accessorType;

    public ElementAccessor(VariableElement variableElement) {
        this( variableElement, AccessorType.FIELD );
    }

    public ElementAccessor(Element element, AccessorType accessorType) {
        super( element );
        this.accessorType = accessorType;
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
        return accessorType;
    }

}
