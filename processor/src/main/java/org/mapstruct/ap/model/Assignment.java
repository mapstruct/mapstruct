/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model;

import java.util.List;
import java.util.Set;
import org.mapstruct.ap.model.common.Type;

/**
 * Assignment represents all kind of manners a source can be assigned to a target.
 *
 * @author Sjaak Derksen
 */
public interface Assignment {

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
     List<Type> getExceptionTypes();

     /**
      * An assignment in itself can wrap another assignment. E.g.:
      * <ul>
      * <li>a MethodReference can wrap a TypeConversion, another MethodReference and ultimately a Simple</li>
      * <li>a TypeConversion can wrap a MethodReference, and ultimately a Simple</li>
      * </ul>
      *
      * @param assignment
      */
     void setAssignment( Assignment assignment );

     /**
      * the source reference being a source-getter, a constant, etc.
      *
      * @return source reference
      */
     String getSourceReference();

     /**
      * Returns whether the implemented assignment is a plain source assignment (Simple assignment)
      * (so not a MethodReference or TypeConversion).
      *
      * @return true when this is a (wrapped) Simple Assignment
      */
     boolean isSimple();
}
