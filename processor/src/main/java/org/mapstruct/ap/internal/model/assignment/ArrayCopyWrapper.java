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

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Decorates the assignment as a Map or Collection constructor
 *
 * @author Sjaak Derksen
 */
public class ArrayCopyWrapper extends AssignmentWrapper {

    private final Type arraysType;
    private final Type targetType;

    public ArrayCopyWrapper(Assignment rhs,
                            String targetPropertyName,
                            Type arraysType,
                            Type targetType,
                            boolean fieldAssignment) {
        super( rhs, fieldAssignment );
        this.arraysType = arraysType;
        this.targetType = targetType;
        rhs.setSourceLocalVarName( rhs.createLocalVarName( targetPropertyName ) );
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<Type>();
        imported.addAll( getAssignment().getImportTypes() );
        imported.add( arraysType );
        imported.add( targetType );
        return imported;
    }

    public boolean isIncludeSourceNullCheck() {
        return true;
    }
}
