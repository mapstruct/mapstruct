/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import org.mapstruct.ap.internal.model.common.Type;

/**
 * A mapper instance field
 *
 * @author Ewald Volkert
 */
public class HelperFinalField implements HelperFieldReference {

    private final Type type;
    private String variableName;

    public HelperFinalField(Type type, String variableName) {
        this.type = type;
        this.variableName = variableName;
    }

    @Override
    public String getVariableName() {
        return variableName;
    }

    @Override
    public Type getType() {
        return type;
    }

}
