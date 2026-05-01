/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.NewInstanceCreation;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

import static org.mapstruct.ap.internal.model.common.Assignment.AssignmentType.DIRECT;

/**
 * This wrapper handles the situation where an assignment is done via the setter and a null check is needed.
 * This is needed when a direct assignment is used, or if the user has chosen the appropriate strategy
 *
 * @author Sjaak Derksen
 */
public class SetterWrapperForCollectionsAndMapsWithNullCheck extends WrapperForCollectionsAndMaps {

    private final Type targetType;
    private final TypeFactory typeFactory;
    private final NewInstanceCreation newInstance;

    public SetterWrapperForCollectionsAndMapsWithNullCheck(Assignment decoratedAssignment,
        List<Type> thrownTypesToExclude,
        Type targetType,
        TypeFactory typeFactory,
        boolean fieldAssignment) {
        super(
            decoratedAssignment,
            thrownTypesToExclude,
            targetType,
            fieldAssignment
        );
        this.targetType = targetType;
        this.typeFactory = typeFactory;
        this.newInstance = NewInstanceCreation.forType( targetType );
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<>( super.getImportTypes() );
        if ( isDirectAssignment() ) {
            imported.addAll( newInstance.getImportTypes() );
            if ( isEnumSet() ) {
                imported.add( typeFactory.getType( EnumSet.class ) );
            }
        }
        if ( isDirectAssignment() || getSourcePresenceCheckerReference() == null ) {
            imported.addAll( getNullCheckLocalVarType().getImportTypes() );
        }
        return imported;
    }

    public NewInstanceCreation getNewInstance() {
        return newInstance;
    }

    public boolean isDirectAssignment() {
        return getType() == DIRECT;
    }

    public boolean isEnumSet() {
        return targetType.isEnumSet();
    }

}
