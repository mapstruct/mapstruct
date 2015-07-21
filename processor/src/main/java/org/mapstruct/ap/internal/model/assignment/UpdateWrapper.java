/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public UpdateWrapper(Assignment decoratedAssignment, List<Type> thrownTypesToExclude, Assignment factoryMethod,
        Type targetImplementationType ) {
        super( decoratedAssignment );
        this.thrownTypesToExclude = thrownTypesToExclude;
        this.factoryMethod = factoryMethod;
        this.targetImplementationType = determineImplType( factoryMethod, targetImplementationType );
    }

    private static Type determineImplType(Assignment factoryMethod, Type targetType) {
        if ( targetType.getImplementationType() != null ) {
            // it's probably a collection or something
            return targetType.getImplementationType();
        }

        if ( factoryMethod == null ) {
            // no factory method means we create a new instance ourself and thus need to import the type
            return targetType;
        }

        return null;
    }

    @Override
    public List<Type> getThrownTypes() {
        List<Type> parentThrownTypes = super.getThrownTypes();
        List<Type> result = new ArrayList<Type>( parentThrownTypes );
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
        Set<Type> imported = new HashSet<Type>();
        imported.addAll( super.getImportTypes() );
        if ( targetImplementationType != null ) {
            imported.add( targetImplementationType );
        }
        return imported;
    }

    public Assignment getFactoryMethod() {
        return factoryMethod;
    }

}
