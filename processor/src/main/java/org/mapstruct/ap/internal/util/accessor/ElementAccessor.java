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
 * An {@link Accessor} that wraps a {@link Element}.
 * Used for getter, setter, filed, constructor, record-class, etc.
 * @author Filip Hrisafov
 * @author Tang Yang
 */
public class ElementAccessor extends AbstractAccessor<Element> {

    private final String name;
    private final AccessorType accessorType;
    private final TypeMirror accessedType;

    public ElementAccessor(VariableElement variableElement, TypeMirror accessedType) {
        this( variableElement, accessedType, AccessorType.FIELD );
    }

    public ElementAccessor(Element element, TypeMirror accessedType, String name) {
        super( element );
        this.name = name;
        this.accessedType = accessedType;
        this.accessorType = AccessorType.PARAMETER;
    }

    public ElementAccessor(Element element, TypeMirror accessedType, AccessorType accessorType) {
        super( element );
        this.accessedType = accessedType;
        this.accessorType = accessorType;
        this.name = null;
    }

    @Override
    public TypeMirror getAccessedType() {
        return accessedType;
    }

    @Override
    public String getSimpleName() {
        return name != null ? name : super.getSimpleName();
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
