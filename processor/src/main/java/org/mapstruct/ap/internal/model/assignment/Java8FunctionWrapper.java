/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Wraps the assignment in a Function to be used in Java 8 map methods
 *
 * @author Filip Hrisafov
 */
public class Java8FunctionWrapper extends AssignmentWrapper {

    private final Type functionType;

    public Java8FunctionWrapper(Assignment decoratedAssignment) {
        this( decoratedAssignment, null );
    }

    public Java8FunctionWrapper(Assignment decoratedAssignment, Type functionType) {
        super( decoratedAssignment, false );
        this.functionType = functionType;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<>( super.getImportTypes() );
        if ( isDirectAssignment() && functionType != null ) {
            imported.add( functionType );
        }
        return imported;
    }

    /**
     *
     * @return {@code true} if the wrapped assignment is
     * {@link Assignment.AssignmentType#DIRECT}, {@code false} otherwise
     */
    public boolean isDirectAssignment() {
        return getAssignment().getType() == AssignmentType.DIRECT;
    }

}
