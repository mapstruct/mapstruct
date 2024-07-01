/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Represents a constructor that is used for mapping keys of a {@link MapMappingMethod}.
 *
 * @author Oliver Erhart
 */
public class MapKeyMappingConstructor extends ModelElement implements Constructor {

    private final String name;
    private final String delegateName;
    private final boolean invokeSuperConstructor;

    public MapKeyMappingConstructor(String name, String delegateName, boolean invokeSuperConstructor) {
        this.name = name;
        this.delegateName = delegateName;
        this.invokeSuperConstructor = invokeSuperConstructor;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDelegateName() {
        return delegateName;
    }

    public boolean isInvokeSuperConstructor() {
        return invokeSuperConstructor;
    }
}
