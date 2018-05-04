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
                         String targetPropertyName ) {
        super( rhs, fieldAssignment );
        this.thrownTypesToExclude = thrownTypesToExclude;
        String desiredName = Nouns.singularize( targetPropertyName );
        rhs.setSourceLocalVarName( rhs.createLocalVarName( desiredName ) );
        adderType = first( getSourceType().determineTypeArguments( Collection.class ) );
    }

    @Override
    public List<Type> getThrownTypes() {
        List<Type> parentThrownTypes = super.getThrownTypes();
        List<Type> result = new ArrayList<Type>( parentThrownTypes );
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

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<Type>();
        imported.addAll( super.getImportTypes() );
        imported.add( adderType.getTypeBound() );
        return imported;
    }

}
