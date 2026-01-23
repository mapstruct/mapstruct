/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Strings;

/**
 * @author Filip Hrisafov
 */
public class OptionalGetWrapper extends AssignmentWrapper {

    private final Type optionalType;

    public OptionalGetWrapper(Assignment decoratedAssignment, Type optionalType) {
        super( decoratedAssignment, false );
        this.optionalType = optionalType;
    }

    public Type getOptionalType() {
        return optionalType;
    }

    @Override
    public String toString() {
        if ( optionalType.getFullyQualifiedName().equals( "java.util.Optional" ) ) {
            return getAssignment() + ".get()";
        }
        return getAssignment() + ".getAs" + Strings.capitalize( optionalType.getOptionalBaseType().getName() ) + "()";
    }
}
