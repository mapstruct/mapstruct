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

    public ExecutableElementAccessor(ExecutableElement element) {
        super( element );
    }

    @Override
    public TypeMirror getAccessedType() {
        return element.getReturnType();
    }

    @Override
    public ExecutableElement getExecutable() {
        return element;
    }
}
