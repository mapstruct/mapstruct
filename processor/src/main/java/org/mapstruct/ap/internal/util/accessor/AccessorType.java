/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

public enum AccessorType {

    PARAMETER,
    FIELD,
    GETTER,
    SETTER,
    ADDER,
    MAP,
    PRESENCE_CHECKER;

    public boolean isFieldAssignment() {
        return this == FIELD || this == PARAMETER;
    }
}
