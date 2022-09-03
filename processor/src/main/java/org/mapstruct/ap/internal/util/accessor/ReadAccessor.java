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
 * @author Filip Hrisafov
 */
public interface ReadAccessor extends Accessor {

    String getReadValueSource();

    static ReadAccessor fromField(VariableElement variableElement) {
        return new ReadDelegateAccessor( new FieldElementAccessor( variableElement ) ) {
            @Override
            public String getReadValueSource() {
                return getSimpleName();
            }
        };
    }

    static ReadAccessor fromGetter(ExecutableElement element, TypeMirror accessedType) {
        return new ReadDelegateAccessor( new ExecutableElementAccessor( element, accessedType, AccessorType.GETTER ) ) {
            @Override
            public String getReadValueSource() {
                return getSimpleName() + "()";
            }
        };
    }
}
