/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import java.util.Collections;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

public class ListElementAccessor implements ReadAccessor {

    private final ReadAccessor listAccessor;
    private final TypeMirror elementTypeMirror;
    private final int index;

    public ListElementAccessor(ReadAccessor listAccessor, TypeMirror elementTypeMirror, int index) {
        this.listAccessor = listAccessor;
        this.elementTypeMirror = elementTypeMirror;
        this.index = index;
    }

    @Override
    public TypeMirror getAccessedType() {
        return elementTypeMirror;
    }

    @Override
    public String getSimpleName() {
        return listAccessor.getSimpleName() + "[" + index + "]";
    }

    @Override
    public Set<Modifier> getModifiers() {
        return Collections.emptySet();
    }

    @Override
    public Element getElement() {
        return listAccessor.getElement();
    }

    @Override
    public AccessorType getAccessorType() {
        return AccessorType.GETTER;
    }

    @Override
    public String getReadValueSource() {
        return listAccessor.getReadValueSource() + ".get( " + index + " )";
    }

}
