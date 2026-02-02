/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Wraps the assignment in a target setter.
 *
 * @author Sjaak Derksen
 */
public class SetterWrapper extends AssignmentWrapper {

    private final List<Type> thrownTypesToExclude;
    private final boolean includeSourceNullCheck;
    private final boolean setExplicitlyToNull;
    private final boolean setExplicitlyToDefault;
    private final boolean mustCastForNull;
    private final Type targetImplementationType;

    public SetterWrapper(Assignment rhs,
                         List<Type> thrownTypesToExclude,
                         boolean fieldAssignment,
                         boolean includeSourceNullCheck,
                         boolean setExplicitlyToNull,
                         boolean setExplicitlyToDefault,
                         boolean mustCastForNull,
                         Type targetImplementationType) {

        super( rhs, fieldAssignment );
        this.thrownTypesToExclude = thrownTypesToExclude;
        this.includeSourceNullCheck = includeSourceNullCheck;
        this.setExplicitlyToDefault = setExplicitlyToDefault;
        this.setExplicitlyToNull = setExplicitlyToNull;
        this.mustCastForNull = mustCastForNull;
        this.targetImplementationType = targetImplementationType;
    }

    public SetterWrapper(Assignment rhs, List<Type> thrownTypesToExclude, boolean fieldAssignment  ) {
        super( rhs, fieldAssignment );
        this.thrownTypesToExclude = thrownTypesToExclude;
        this.includeSourceNullCheck = false;
        this.setExplicitlyToNull = false;
        this.setExplicitlyToDefault = false;
        this.mustCastForNull = false;
        this.targetImplementationType = null;
    }

    @Override
    public List<Type> getThrownTypes() {
        List<Type> parentThrownTypes = super.getThrownTypes();
        List<Type> result = new ArrayList<>( parentThrownTypes );
        for ( Type thrownTypeToExclude : thrownTypesToExclude ) {
            for ( Type parentThrownType : parentThrownTypes ) {
                if ( parentThrownType.isAssignableTo( thrownTypeToExclude ) ) {
                    result.remove( parentThrownType );
                }
            }
        }
        return result;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<>(super.getImportTypes());
        if ( isSetExplicitlyToNull() && isMustCastForNull() ) {
            imported.add( targetImplementationType );
        }
        return imported;
    }

    public boolean isSetExplicitlyToNull() {
        return setExplicitlyToNull;
    }

    public boolean isSetExplicitlyToDefault() {
        return setExplicitlyToDefault;
    }

    public boolean isIncludeSourceNullCheck() {
        return includeSourceNullCheck;
    }

    public boolean isMustCastForNull() {
        return mustCastForNull;
    }
}
