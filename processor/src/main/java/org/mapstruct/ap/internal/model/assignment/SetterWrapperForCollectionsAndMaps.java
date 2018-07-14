/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import java.util.List;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * This wrapper handles the situation where an assignment is done via the setter, without doing anything special.
 *
 * @author Sjaak Derksen
 */
public class SetterWrapperForCollectionsAndMaps extends WrapperForCollectionsAndMaps {

    public SetterWrapperForCollectionsAndMaps(Assignment decoratedAssignment,
        List<Type> thrownTypesToExclude,
        Type targetType,
        boolean fieldAssignment) {

        super(
            decoratedAssignment,
            thrownTypesToExclude,
            targetType,
            fieldAssignment
        );
    }
}
