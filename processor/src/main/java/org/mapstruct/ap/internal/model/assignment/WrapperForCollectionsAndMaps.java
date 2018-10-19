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

/**
 * This is the base class for the {@link GetterWrapperForCollectionsAndMaps} and
 * {@link SetterWrapperForCollectionsAndMaps}
 *
 * @author Sjaak Derksen
 */
public class WrapperForCollectionsAndMaps extends AssignmentWrapper {

    private final List<Type> thrownTypesToExclude;
    private final String nullCheckLocalVarName;
    private final Type nullCheckLocalVarType;

    public WrapperForCollectionsAndMaps(Assignment rhs,
                                        List<Type> thrownTypesToExclude,
                                        Type targetType,
                                        boolean fieldAssignment) {

        super( rhs, fieldAssignment );

        this.thrownTypesToExclude = thrownTypesToExclude;
        if ( rhs.getType() == AssignmentType.DIRECT && rhs.getSourceType() != null ) {
            this.nullCheckLocalVarType = rhs.getSourceType();
        }
        else {
            this.nullCheckLocalVarType = targetType;
        }
        this.nullCheckLocalVarName =  rhs.createLocalVarName( nullCheckLocalVarType.getName() );
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

    public String getNullCheckLocalVarName() {
        return nullCheckLocalVarName;
    }

    public Type getNullCheckLocalVarType() {
        return nullCheckLocalVarType;
    }
}
