/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;

public class CanonicalConstructor extends ModelElement implements Constructor {

    private final String name;
    private final List<Parameter> parameters;

    public CanonicalConstructor(String name, List<Parameter> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<>();
        for ( Parameter parameter : parameters ) {
            types.addAll( parameter.getType().getImportTypes() );
        }
        return types;
    }

}
