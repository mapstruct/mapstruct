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

import org.mapstruct.ap.model.assignment.Assignment;
import java.util.List;
import javax.lang.model.type.TypeMirror;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;

/**
 *
 * @author Sjaak Derksen
 */
public interface MappingResolver {

    /**
     * returns a parameter assignment
     *
     * @param mappingMethod target mapping method
     * @param mappedElement used for error messages
     * @param sourceType parameter to match
     * @param targetType return type to match
     * @param targetPropertyName name of the target property
     * @param dateFormat used for formatting dates in build in methods that need context information
     * @param qualifiers used for further select the appropriate mapping method based on class and name
     * @param sourceReference call to source type as string
     *
     * @return an assignment to a method parameter, which can either be:
     * <ol>
     * <li>MethodReference</li>
     * <li>TypeConversion</li>
     * <li>Direct Assignment (empty TargetAssignment)</li>
     * <li>null, no assignment found</li>
     * </ol>
     */
    Assignment getTargetAssignment(
            Method mappingMethod,
            String mappedElement,
            Type sourceType,
            Type targetType,
            String targetPropertyName,
            String dateFormat,
            List<TypeMirror> qualifiers,
            String sourceReference );

}
