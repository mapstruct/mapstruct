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
package org.mapstruct.ap.model.assignment;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.ap.model.common.Type;

/**
 * Wraps the assignment in a target setter.
 *
 * @author Sjaak Derksen
 */
public class UpdateWrapper extends AssignmentWrapper {

    private final List<Type> exceptionTypesToExclude;
    private final Assignment factoryMethod;

    public UpdateWrapper(Assignment decoratedAssignment, List<Type> exceptionTypesToExclude,
        Assignment factoryMethod ) {
        super( decoratedAssignment );
        this.exceptionTypesToExclude = exceptionTypesToExclude;
        this.factoryMethod = factoryMethod;
    }

    @Override
    public List<Type> getExceptionTypes() {
        List<Type> parentExceptionTypes = super.getExceptionTypes();
        List<Type> result = new ArrayList<Type>( parentExceptionTypes );
        for ( Type exceptionTypeToExclude : exceptionTypesToExclude ) {
            for ( Type parentExceptionType : parentExceptionTypes ) {
                if ( parentExceptionType.isAssignableTo( exceptionTypeToExclude ) ) {
                    result.remove( parentExceptionType );
                }
            }
        }
        return result;
    }

    public Assignment getFactoryMethod() {
        return factoryMethod;
    }

}
