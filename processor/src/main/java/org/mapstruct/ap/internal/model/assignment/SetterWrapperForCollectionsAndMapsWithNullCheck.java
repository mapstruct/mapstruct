/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.internal.model.assignment;

import static org.mapstruct.ap.internal.model.common.Assignment.AssignmentType.DIRECT;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

/**
 * This wrapper handles the situation where an assignment is done via the setter and a null check is needed.
 * This is needed when a direct assignment is used, or if the user has chosen the appropriate strategy
 *
 * @author Sjaak Derksen
 */
public class SetterWrapperForCollectionsAndMapsWithNullCheck extends WrapperForCollectionsAndMaps {

    private final Type targetType;
    private final TypeFactory typeFactory;
    private final boolean mapNullToDefault;

    public SetterWrapperForCollectionsAndMapsWithNullCheck(Assignment decoratedAssignment,
        List<Type> thrownTypesToExclude,
        Type targetType,
        TypeFactory typeFactory,
        boolean fieldAssignment,
        boolean mapNullToDefault) {
        super(
            decoratedAssignment,
            thrownTypesToExclude,
            targetType,
            fieldAssignment
        );
        this.targetType = targetType;
        this.typeFactory = typeFactory;
        this.mapNullToDefault = mapNullToDefault;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<Type>( super.getImportTypes() );
        if ( isDirectAssignment() ) {
            if ( targetType.getImplementationType() != null ) {
                imported.addAll( targetType.getImplementationType().getImportTypes() );
            }
            else {
                imported.addAll( targetType.getImportTypes() );
            }

            if ( isEnumSet() ) {
                imported.add( typeFactory.getType( EnumSet.class ) );
            }
        }
        if (isDirectAssignment() || getSourcePresenceCheckerReference() == null ) {
            imported.addAll( getNullCheckLocalVarType().getImportTypes() );
        }
        return imported;
    }

    public boolean isDirectAssignment() {
        return getType() == DIRECT;
    }

    public boolean isEnumSet() {
        return "java.util.EnumSet".equals( targetType.getFullyQualifiedName() );
    }

    public boolean isMapNullToDefault() {
        return mapNullToDefault;
    }

}
