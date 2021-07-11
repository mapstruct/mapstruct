/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Decorates an assignment as a return variable.
 *
 * @author Ben Zegveld
 */
public class ReturnWrapper extends AssignmentWrapper {

    public ReturnWrapper(Assignment decoratedAssignment) {
        super( decoratedAssignment, false );
    }

    @Override
    public List<Type> getThrownTypes() {
        return Collections.emptyList();
    }

    @Override
    public Set<Type> getImportTypes() {
        return new HashSet<>( getAssignment().getImportTypes() );
    }

}
