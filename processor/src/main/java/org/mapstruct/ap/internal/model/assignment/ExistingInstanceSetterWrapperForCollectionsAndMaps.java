/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import static org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism.ALWAYS;

import java.util.List;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism;

/**
 * This wrapper handles the situation where an assignment is done for an update method.
 *
 * In case of a pre-existing target the wrapper checks if there is an collection or map initialized on the target bean
 * (not null). If so it uses the addAll (for collections) or putAll (for maps). The collection / map is cleared in case
 * of a pre-existing target {@link org.mapstruct.MappingTarget }before adding the source entries.
 *
 * If there is no pre-existing target, or the target Collection / Map is not initialized (null) the setter is used to
 * create a new Collection / Map with the copy constructor.
 *
 * @author Sjaak Derksen
 */
public class ExistingInstanceSetterWrapperForCollectionsAndMaps
    extends SetterWrapperForCollectionsAndMapsWithNullCheck {

    private final boolean includeSourceNullCheck;

    public ExistingInstanceSetterWrapperForCollectionsAndMaps(Assignment decoratedAssignment,
        List<Type> thrownTypesToExclude,
        Type targetType,
        NullValueCheckStrategyPrism nvms,
        TypeFactory typeFactory,
        boolean fieldAssignment,
        boolean mapNullToDefault) {

        super(
            decoratedAssignment,
            thrownTypesToExclude,
            targetType,
            typeFactory,
            fieldAssignment,
            mapNullToDefault
        );
        this.includeSourceNullCheck = ALWAYS == nvms;
    }

    public boolean isIncludeSourceNullCheck() {
        return includeSourceNullCheck;
    }
}
