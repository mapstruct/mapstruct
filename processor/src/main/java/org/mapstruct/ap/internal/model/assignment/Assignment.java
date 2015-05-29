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

import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;

/**
 * Assignment represents all kind of manners a source can be assigned to a target.
 *
 * @author Sjaak Derksen
 */
public interface Assignment {

    public static enum AssignmentType {
        /** assignment is direct */
        DIRECT,
        /** assignment is type converted */
        TYPE_CONVERTED,
        /** assignment is mapped (builtin/custom) */
        MAPPED,
        /** 2 mapping methods (builtin/custom) are applied to get the target */
        MAPPED_TWICE,
        /** assignment is first mapped (builtin/custom), then the result is type converted */
        MAPPED_TYPE_CONVERTED,
        /** assignment is first type converted, and then mapped (builtin/custom) */
        TYPE_CONVERTED_MAPPED
    }

    /**
     * returns all types required as import by the assignment statement.
     *
     * @return imported types
     */
    Set<Type> getImportTypes();

    /**
     * returns all types exception types thrown by this assignment.
     *
     * @return exceptions thrown
     */
     List<Type> getThrownTypes();

    /**
     * An assignment in itself can wrap another assignment. E.g.:
     * <ul>
     * <li>a MethodReference can wrap a TypeConversion, another MethodReference and ultimately a Simple</li>
     * <li>a TypeConversion can wrap a MethodReference, and ultimately a Simple</li>
     * </ul>
     *
     * @param assignment the assignment to set
     */
    void setAssignment(Assignment assignment);

    /**
     * the source reference being a source-getter, a constant, etc.
     *
     * @return source reference
     */
    String getSourceReference();

    /**
     * Returns whether the type of assignment
     *
     * @return {@link  AssignmentType}
     */
    AssignmentType getType();


    boolean isUpdateMethod();
}
