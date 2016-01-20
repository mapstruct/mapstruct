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
import java.util.List;

import org.mapstruct.ap.internal.model.common.Type;

/**
 * Wraps an update-assignment in a hasX check and sets the target property to {@code null}, if the source is
 * {@code null}.
 *
 * @author Sean Huang
 */
public class UpdateHasCheckWrapper extends AssignmentWrapper {

    private final List<Type> thrownTypesToExclude;
    private final String sourceHasMethod;

    public UpdateHasCheckWrapper( Assignment decoratedAssignment, List<Type> thrownTypesToExclude,
        String sourceHasMethod) {
        super( decoratedAssignment );
        this.thrownTypesToExclude = thrownTypesToExclude;
        this.sourceHasMethod = sourceHasMethod;
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
    public String getSourceHasMethod() {
        return sourceHasMethod;
    }
}
