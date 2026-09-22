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
 * An {@link Accessor} that can be used for reading a property from a bean.
 *
 * @author Filip Hrisafov
 */
public interface ReadAccessor extends Accessor {

    String getReadValueSource();

    static ReadAccessor fromField(VariableElement variableElement, TypeMirror accessedType, Nullability nullability) {
        return new ReadDelegateAccessor( new ElementAccessor( variableElement, accessedType, nullability ) ) {
            @Override
            public String getReadValueSource() {
                return getSimpleName();
            }
        };
    }

    static ReadAccessor fromRecordComponent(VariableElement element, TypeMirror accessedType,
                                            NullabilityResolver nullabilityResolver) {
        return new ReadDelegateAccessor( new ElementAccessor( element, accessedType, AccessorType.GETTER,
               nullabilityResolver.getParameterNullability( element )
               ) ) {
            @Override
            public String getReadValueSource() {
                return getSimpleName() + "()";
            }
        };
    }

    static ReadAccessor fromGetter(ExecutableElement element, TypeMirror accessedType,
                                   NullabilityResolver nullabilityResolver) {
        return new ReadDelegateAccessor( new ElementAccessor( element, accessedType, AccessorType.GETTER,
                nullabilityResolver.getMethodeReturnTypeNullability( element )) ) {
            @Override
            public String getReadValueSource() {
                return getSimpleName() + "()";
            }
        };
    }
}
