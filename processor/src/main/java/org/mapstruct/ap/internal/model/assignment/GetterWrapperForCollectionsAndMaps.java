/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * This wrapper handles the situation were an assignment must be done via a target getter method because there
 * is no setter available.
 *
 * The wrapper checks if there is an collection or map initialized on the target bean (not null). If so it uses the
 * addAll (for collections) or putAll (for maps). The collection / map is cleared in case of a pre-existing target
 * {@link org.mapstruct.MappingTarget }before adding the source entries. The goal is that the same collection / map
 * is used as target.
 *
 * Nothing can be added if the getter on the target returns null.
 *
 * @author Sjaak Derksen
 */
public class GetterWrapperForCollectionsAndMaps extends WrapperForCollectionsAndMaps {

    /**
     * @param decoratedAssignment source RHS
     * @param thrownTypesToExclude set of types to exclude from re-throwing
     * @param targetType the target type
     * @param fieldAssignment true when this the assignment is to a field rather than via accessors
     */
    public GetterWrapperForCollectionsAndMaps(Assignment decoratedAssignment,
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

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<>( super.getImportTypes() );
        if ( getSourcePresenceCheckerReference() == null ) {
            imported.addAll( getNullCheckLocalVarType().getImportTypes() );
        }
        return imported;
    }
}
