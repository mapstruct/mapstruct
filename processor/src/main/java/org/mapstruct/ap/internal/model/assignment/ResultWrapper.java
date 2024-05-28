/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import org.mapstruct.ap.internal.model.common.Assignment;

/**
 * Decorates an assignment as a return variable.
 *
 * @author Ben Zegveld
 */
public class ResultWrapper extends AssignmentWrapper {

    public ResultWrapper(Assignment decoratedAssignment) {
        super( decoratedAssignment, false );
    }
}
