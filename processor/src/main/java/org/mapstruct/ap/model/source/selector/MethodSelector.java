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
package org.mapstruct.ap.model.source.selector;

import java.util.List;

import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.SourceMethod;

/**
 * @author Sjaak Derksen
 */
public interface MethodSelector {

    /**
     * Selects a method
     *
     * @param <T> either SourceMethod or BuiltInMethod
     * @param mappingMethod mapping method, defined in Mapper for which this selection is carried out
     * @param methods set from available methods
     * @param parameterType parameter type that should be matched
     * @param returnType return type that should be matched
     * @param targetPropertyName some information can be derived from the target property
     *
     * @return list of methods that passes the matching process
     */
    <T extends Method> List<T> getMatchingMethods(
        SourceMethod mappingMethod,
        Iterable<T> methods,
        Type parameterType,
        Type returnType,
        String targetPropertyName
    );
}
