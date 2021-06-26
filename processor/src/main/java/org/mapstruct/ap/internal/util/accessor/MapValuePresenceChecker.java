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

/**
 * An {@link Accessor} that wraps a Map value.
 *
 * @author Christian Kosmowski
 */
public class MapValuePresenceChecker implements Accessor {

    private final Element element;
    private final TypeMirror valueTypeMirror;
    private final String simpleName;

    public MapValuePresenceChecker(Element element, TypeMirror valueTypeMirror, String simpleName) {
        this.element = element;
        this.valueTypeMirror = valueTypeMirror;
        this.simpleName = simpleName;
    }

    @Override
    public TypeMirror getAccessedType() {
        return valueTypeMirror;
    }

    @Override
    public String getSimpleName() {
        return this.simpleName;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return Collections.emptySet();
    }

    @Override
    public Element getElement() {
        return this.element;
    }

    @Override
    public AccessorType getAccessorType() {
        return AccessorType.MAP_CONTAINS;
    }
}
