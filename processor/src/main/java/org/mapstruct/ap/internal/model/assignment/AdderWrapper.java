/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Nouns;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * Wraps the assignment in a target setter.
 *
 * @author Sjaak Derksen
 */
public class AdderWrapper extends AssignmentWrapper {

    private final List<Type> thrownTypesToExclude;
    private final Type adderType;

    public AdderWrapper( Assignment rhs,
                         List<Type> thrownTypesToExclude,
                         boolean fieldAssignment,
                         String adderIteratorName ) {
        super( rhs, fieldAssignment );
        this.thrownTypesToExclude = thrownTypesToExclude;
        // a method local var has been added earlier.


        // localVar is iteratorVariable
        String desiredName = Nouns.singularize( adderIteratorName );
        rhs.setSourceLoopVarName( rhs.createUniqueVarName( desiredName ) );
        if ( getSourceType().isCollectionType() ) {
            adderType = first( getSourceType().determineTypeArguments( Collection.class ) );
        }
        else if ( getSourceType().isArrayType() ) {
            adderType = getSourceType().getComponentType();
        }
        else { // iterable
            adderType = first( getSourceType().determineTypeArguments( Iterable.class ) );
        }
    }

    @Override
    public List<Type> getThrownTypes() {
        List<Type> parentThrownTypes = super.getThrownTypes();
        List<Type> result = new ArrayList<>( parentThrownTypes );
        for ( Type thrownTypeToExclude : thrownTypesToExclude ) {
            for ( Type parentExceptionType : parentThrownTypes ) {
                if ( parentExceptionType.isAssignableTo( thrownTypeToExclude ) ) {
                    result.remove( parentExceptionType );
                }
            }
        }
        return result;
    }

    public Type getAdderType() {
        return adderType;
    }

    public boolean isIncludeSourceNullCheck() {
        return true;
    }

    public boolean isSetExplicitlyToNull() {
        return false;
    }

    public boolean isSetExplicitlyToDefault() {
        return false;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<>( super.getImportTypes() );
        imported.add( adderType.getTypeBound() );
        return imported;
    }

}
