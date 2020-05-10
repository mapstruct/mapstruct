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
public class NestedExecutableElementAccessor extends ExecutableElementAccessor {

    public NestedExecutableElementAccessor(ExecutableElement element, TypeMirror accessedType) {
        super( element, accessedType, AccessorType.PRESENCE_CHECKER );
    }

}
