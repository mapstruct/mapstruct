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
package org.mapstruct.ap.internal.model.common;

import java.util.List;
import java.util.Set;

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
     *
     * @return true when the source reference is the source parameter (and not a property of the source parameter type)
     */
    boolean isSourceReferenceParameter();

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
     * Creates an unique safe (local) variable name.
     *
     * @param desiredName the desired name
     *
     * @return the desired name, made unique in the scope of the bean mapping.
     */
    String createLocalVarName( String desiredName );

    /**
     * See {@link #setSourceLocalVarName(java.lang.String) }
     *
     * @return local variable name (can be null if not set)
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
     * Replaces the sourceReference at the call site in the assignment in the template with this sourceLocalVarName.
     * The sourceLocalVarName can subsequently be used for e.g. null checking.
     *
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
