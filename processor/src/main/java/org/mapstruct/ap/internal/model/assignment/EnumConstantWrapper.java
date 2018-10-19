/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/**
 *
 * @author Sjaak Derksen
 */
public class EnumConstantWrapper extends AssignmentWrapper {

    private final Type enumType;

    public EnumConstantWrapper(Assignment decoratedAssignment, Type enumType ) {
        super( decoratedAssignment, false );
        this.enumType = enumType;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<>( getAssignment().getImportTypes() );
        imported.add( enumType );
        return imported;
    }

    @Override
    public String toString() {
        return enumType.getName() + "." + getAssignment();
    }

}
