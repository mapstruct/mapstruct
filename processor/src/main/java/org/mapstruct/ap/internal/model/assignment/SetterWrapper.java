/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism;

import static org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism.ALWAYS;

/**
 * Wraps the assignment in a target setter.
 *
 * @author Sjaak Derksen
 */
public class SetterWrapper extends AssignmentWrapper {

    private final List<Type> thrownTypesToExclude;
    private final boolean includeSourceNullCheck;

    public SetterWrapper(Assignment rhs,
                         List<Type> thrownTypesToExclude,
                         boolean fieldAssignment,
                         boolean includeSourceNullCheck ) {

        super( rhs, fieldAssignment );
        this.thrownTypesToExclude = thrownTypesToExclude;
        this.includeSourceNullCheck = includeSourceNullCheck;
    }

    public SetterWrapper(Assignment rhs, List<Type> thrownTypesToExclude, boolean fieldAssignment  ) {
        super( rhs, fieldAssignment );
        this.thrownTypesToExclude = thrownTypesToExclude;
        this.includeSourceNullCheck = false;
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

    public boolean isIncludeSourceNullCheck() {
        return includeSourceNullCheck;
    }

    /**
     * Wraps the assignment in a target setter. include a null check when
     *
     * - Not if source is the parameter iso property, because the null check is than handled by the bean mapping
     * - Not when source is primitive, you can't null check a primitive
     * - The source property is fed to a conversion somehow before its assigned to the target
     * - The user decided to ALLWAYS include a null check
     *
     * @param rhs the source righthand side
     * @param nvcs null value check strategy
     * @param targetType the target type
     *
     * @return include a null check
     */
    public static boolean doSourceNullCheck(Assignment rhs, NullValueCheckStrategyPrism nvcs, Type targetType) {
        return !rhs.isSourceReferenceParameter()
            && !rhs.getSourceType().isPrimitive()
            && (ALWAYS == nvcs || rhs.getType().isConverted()
            || (rhs.getType().isDirect() && targetType.isPrimitive()));
    }
}
