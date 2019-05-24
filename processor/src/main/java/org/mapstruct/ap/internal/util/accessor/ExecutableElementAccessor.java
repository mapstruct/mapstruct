/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

/**
 * An {@link Accessor} that wraps an {@link ExecutableElement}.
 *
 * @author Filip Hrisafov
 */
public class ExecutableElementAccessor extends AbstractAccessor<ExecutableElement> {

    private final TypeMirror accessedType;
    private final AccessorType accessorType;

    public ExecutableElementAccessor(ExecutableElement element, TypeMirror accessedType, AccessorType accessorType) {
        super( element );
        this.accessedType = accessedType;
        this.accessorType = accessorType;
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
        return accessorType;
    }
}
