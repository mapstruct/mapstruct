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
 * Wraps the assignment in a Function to be used in Java 8 map methods
 *
 * @author Filip Hrisafov
 */
public class Java8FunctionWrapper extends AssignmentWrapper {

    private final Type functionType;

    public Java8FunctionWrapper(Assignment decoratedAssignment) {
        this( decoratedAssignment, null );
    }

    public Java8FunctionWrapper(Assignment decoratedAssignment, Type functionType) {
        super( decoratedAssignment, false );
        this.functionType = functionType;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<Type>( super.getImportTypes() );
        if ( isDirectAssignment() && functionType != null ) {
            imported.add( functionType );
        }
        return imported;
    }

    /**
     *
     * @return {@code true} if the wrapped assignment is
     * {@link Assignment.AssignmentType#DIRECT}, {@code false} otherwise
     */
    public boolean isDirectAssignment() {
        return getAssignment().getType() == AssignmentType.DIRECT;
    }

}
