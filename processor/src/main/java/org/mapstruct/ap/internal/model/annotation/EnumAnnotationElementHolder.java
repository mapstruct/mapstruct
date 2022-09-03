/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.annotation;

import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

public class EnumAnnotationElementHolder extends ModelElement {

    private final Type enumClass;
    private final String name;

    public EnumAnnotationElementHolder(Type enumClass, String name) {
        this.enumClass = enumClass;
        this.name = name;
    }

    public Type getEnumClass() {
        return enumClass;
    }

    public String getName() {
        return name;
    }

    @Override
    public Set<Type> getImportTypes() {
        return enumClass.getImportTypes();
    }
}
