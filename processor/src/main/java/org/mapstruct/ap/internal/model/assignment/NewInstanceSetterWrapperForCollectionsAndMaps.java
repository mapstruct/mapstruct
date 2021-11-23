/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import java.util.List;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

/**
 * This wrapper handles the situation where an assignment is done via the setter, while creating the collection or map
 * using a no-args constructor.
 *
 * @author Ben Zegveld
 */
public class NewInstanceSetterWrapperForCollectionsAndMaps extends SetterWrapperForCollectionsAndMapsWithNullCheck {

    private String instanceVar;

    public NewInstanceSetterWrapperForCollectionsAndMaps(Assignment decoratedAssignment,
        List<Type> thrownTypesToExclude,
        Type targetType,
        TypeFactory typeFactory,
        boolean fieldAssignment) {

        super(
            decoratedAssignment,
            thrownTypesToExclude,
            targetType,
            typeFactory,
            fieldAssignment
        );
        this.instanceVar = decoratedAssignment.createUniqueVarName( targetType.getName() );
    }

    public String getInstanceVar() {
        return instanceVar;
    }
}
