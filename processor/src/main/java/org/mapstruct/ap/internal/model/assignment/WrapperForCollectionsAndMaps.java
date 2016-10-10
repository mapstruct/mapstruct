/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
import org.mapstruct.ap.internal.util.Strings;

/**
 * This is the base class for the {@link GetterWrapperForCollectionsAndMaps} and
 * {@link SetterWrapperForCollectionsAndMaps}
 *
 * @author Sjaak Derksen
 */
public class WrapperForCollectionsAndMaps extends AssignmentWrapper {

    private final List<Type> thrownTypesToExclude;
    private final String sourcePresenceChecker;
    private final String localVarName;
    private final Type localVarType;

    public WrapperForCollectionsAndMaps(Assignment decoratedAssignment,
                                        List<Type> thrownTypesToExclude,
                                        String sourcePresenceChecker,
                                        Set<String> existingVariableNames,
                                        Type targetType,
                                        boolean fieldAssignment) {

        super( decoratedAssignment, fieldAssignment );

        this.thrownTypesToExclude = thrownTypesToExclude;
        this.sourcePresenceChecker = sourcePresenceChecker;
        if ( getType() == AssignmentType.DIRECT && getSourceType() != null ) {
            this.localVarType = getSourceType();
        }
        else {
            this.localVarType = targetType;
        }
        this.localVarName = Strings.getSaveVariableName( localVarType.getName(), existingVariableNames );
        existingVariableNames.add( this.localVarName );
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
        imported.add( localVarType );
        imported.addAll( localVarType.getTypeParameters() );
        return imported;
    }

    public String getSourcePresenceChecker() {
        return sourcePresenceChecker;
    }

    public String getLocalVarName() {
        return localVarName;
    }

    public Type getLocalVarType() {
        return localVarType;
    }
}
