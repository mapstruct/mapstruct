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
    protected final TypeMirror accessedType;

    public ParameterElementAccessor(Element element, TypeMirror accessedType, String name) {
        super( element );
        this.name = name;
        this.accessedType = accessedType;
    }

    @Override
    public String getSimpleName() {
        return name != null ? name : super.getSimpleName();
    }

    @Override
    public TypeMirror getAccessedType() {
        return accessedType;
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
