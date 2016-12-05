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

import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;

/**
 * Assignment represents all kind of manners a source can be assigned to a target.
 *
 * @author Sjaak Derksen
 */
public interface Assignment {

    enum AssignmentType {
        /** assignment is direct */
        DIRECT( true, false ),
        /** assignment is type converted */
        TYPE_CONVERTED( false, true ),
        /** assignment is mapped (builtin/custom) */
        MAPPED( false, false ),
        /** 2 mapping methods (builtin/custom) are applied to get the target */
        MAPPED_TWICE( false, false ),
        /** assignment is first mapped (builtin/custom), then the result is type converted */
        MAPPED_TYPE_CONVERTED( false, true ),
        /** assignment is first type converted, and then mapped (builtin/custom) */
        TYPE_CONVERTED_MAPPED( false, true );

        private final boolean direct;
        private final boolean converted;

        AssignmentType( boolean isDirect, boolean isConverted ) {
            this.direct = isDirect;
            this.converted = isConverted;
        }

        public boolean isDirect() {
            return direct;
        }

        public boolean isConverted() {
            return converted;
        }

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
     * the source reference being a source-getter, a constant, nested method call, etc.
     *
     * @return source reference
     */
    String getSourceReference();

    /**
     * the source presence checker reference
     *
     * @return source reference
     */
    String getSourcePresenceCheckerReference();

    /**
     * the source type used in the matching process
     *
     * @return source type (can be null)
     */
    Type getSourceType();

    /**
     * safe (local) element variable name when dealing with collections.
     *
     * @param desiredName the desired name
     * @return the desired name, made unique
     */
    String createLocalVarName( String desiredName );

    /**
     * a local variable name for supporting a null check and avoiding executing a nested method forged method twice
     *
     * @return local variable name (can be null)
     */
    String getSourceLocalVarName();

    /**
     * Returns the source parameter name, to which this assignment applies. Note: the source parameter itself might
     * be mapped by this assignment, or one of its properties
     *
     * @return  the source parameter name
     */
    String getSourceParameterName();

    /**
     * Use sourceLocalVarName iso sourceReference
     * @param sourceLocalVarName source local variable name
     */
    void setSourceLocalVarName(String sourceLocalVarName);

    /**
     * Returns whether the type of assignment
     *
     * @return {@link  AssignmentType}
     */
    AssignmentType getType();

    boolean isCallingUpdateMethod();
}
