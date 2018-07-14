/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * An {@link Accessor} that wraps a {@link VariableElement}.
 *
 * @author Filip Hrisafov
 */
public class VariableElementAccessor extends AbstractAccessor<VariableElement> {

    public VariableElementAccessor(VariableElement element) {
        super( element );
    }

    @Override
    public TypeMirror getAccessedType() {
        return element.asType();
    }

    @Override
    public ExecutableElement getExecutable() {
        return null;
    }
}
