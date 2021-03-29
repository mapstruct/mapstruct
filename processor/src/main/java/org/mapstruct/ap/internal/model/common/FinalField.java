/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

/**
 * A mapper instance field, initialized as null
 *
 * @author Sjaak Derksen
 */
public class FinalField implements FieldReference {

    private final Type type;
    private String variableName;

    public FinalField(Type type, String variableName) {
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
