/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * An {@link Accessor} that wraps a {@link Element}.
 * Used for getter, setter, filed, constructor, record-class, etc.
 * @author Filip Hrisafov
 * @author Tang Yang
 */
public class ElementAccessor implements Accessor {

    private final Element element;
    private final String name;
    private final AccessorType accessorType;
    private final TypeMirror accessedType;

    public ElementAccessor(VariableElement variableElement, TypeMirror accessedType) {
        this( variableElement, accessedType, AccessorType.FIELD );
    }

    public ElementAccessor(Element element, TypeMirror accessedType, String name) {
        this.element = element;
        this.name = name;
        this.accessedType = accessedType;
        this.accessorType = AccessorType.PARAMETER;
    }

    public ElementAccessor(Element element, TypeMirror accessedType, AccessorType accessorType) {
        this.element = element;
        this.accessedType = accessedType;
        this.accessorType = accessorType;
        this.name = null;
    }

    @Override
    public TypeMirror getAccessedType() {
        return accessedType != null ? accessedType : element.asType();
    }

    @Override
    public String getSimpleName() {
        return name != null ? name : element.getSimpleName().toString();
    }

    @Override
    public Set<Modifier> getModifiers() {
        return element.getModifiers();
    }

    @Override
    public Element getElement() {
        return element;
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
