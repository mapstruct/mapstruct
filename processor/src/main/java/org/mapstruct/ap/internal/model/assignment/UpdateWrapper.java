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
public class UpdateWrapper extends AssignmentWrapper {

    private final List<Type> thrownTypesToExclude;
    private final Assignment factoryMethod;
    private final Type targetImplementationType;
    private final boolean includeSourceNullCheck;
    private final boolean setExplicitlyToNull;
    private final boolean setExplicitlyToDefault;

    public UpdateWrapper( Assignment decoratedAssignment,
                          List<Type> thrownTypesToExclude,
                          Assignment factoryMethod,
                          boolean fieldAssignment,
                          Type targetType,
                          boolean includeSourceNullCheck,
                          boolean setExplicitlyToNull,
                          boolean setExplicitlyToDefault ) {
        super( decoratedAssignment, fieldAssignment );
        this.thrownTypesToExclude = thrownTypesToExclude;
        this.factoryMethod = factoryMethod;
        this.targetImplementationType = determineImplType( factoryMethod, targetType );
        this.includeSourceNullCheck = includeSourceNullCheck;
        this.setExplicitlyToDefault = setExplicitlyToDefault;
        this.setExplicitlyToNull = setExplicitlyToNull;
    }

    private static Type determineImplType(Assignment factoryMethod, Type targetType) {
        if ( factoryMethod != null ) {
            //If we have factory method then we won't use the targetType
            return null;
        }
        if ( targetType.getImplementationType() != null ) {
            // it's probably a collection or something
            return targetType.getImplementationType();
        }

        // no factory method means we create a new instance ourselves and thus need to import the type
        return targetType;
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
        Set<Type> imported = new HashSet<>( super.getImportTypes() );
        if ( factoryMethod != null ) {
            imported.addAll( factoryMethod.getImportTypes() );
        }
        if ( targetImplementationType != null ) {
            imported.add( targetImplementationType );
            imported.addAll( targetImplementationType.getTypeParameters() );
        }
        return imported;
    }

    public Assignment getFactoryMethod() {
        return factoryMethod;
    }

    public boolean isIncludeSourceNullCheck() {
        return includeSourceNullCheck;
    }

    public boolean isSetExplicitlyToNull() {
        return setExplicitlyToNull;
    }

    public boolean isSetExplicitlyToDefault() {
        return setExplicitlyToDefault;
    }
}
