/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

public class ConstructorParameter extends ModelElement {

    private final Type type;
    private final String name;
    private final boolean isAnnotatedMapper;
    private final boolean isNeededForSuper;

    public ConstructorParameter(Type type, String name, boolean isAnnotatedMapper, boolean isNeededForSuper) {
        this.type = type;
        this.name = name;
        this.isAnnotatedMapper = isAnnotatedMapper;
        this.isNeededForSuper = isNeededForSuper;
    }
    public ConstructorParameter(Type type, String name, boolean isAnnotatedMapper) {
        this.type = type;
        this.name = name;
        this.isAnnotatedMapper = isAnnotatedMapper;
        this.isNeededForSuper = true;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isAnnotatedMapper() {
        return isAnnotatedMapper;
    }

    public boolean isNeededForSuper() {
        return isNeededForSuper;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.asSet( type );
    }

}
