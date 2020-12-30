/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import org.mapstruct.ap.internal.model.common.Type;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.util.Collections;
import java.util.Set;

/**
 * An {@link Accessor} that wraps a Map value.
 *
 * @author Christian Kosmowski
 */
public class MapValuePresenceChecker implements Accessor {

    private final Type mapType;
    private final TypeMirror valueTypeMirror;
    private final String simpleName;

    public MapValuePresenceChecker(Type mapType, TypeMirror valueTypeMirror, String simpleName) {
        this.mapType = mapType;
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
        return mapType.getTypeElement().getEnclosedElements().stream().filter(element -> {
            return element.getSimpleName().contentEquals("containsKey");
        }).findFirst().orElse(null);
    }

    @Override
    public AccessorType getAccessorType() {
        return AccessorType.MAP;
    }
}
