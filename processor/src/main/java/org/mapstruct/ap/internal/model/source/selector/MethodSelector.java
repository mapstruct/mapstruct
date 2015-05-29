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
package org.mapstruct.ap.internal.model.source.selector;

import java.util.List;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * Implementations select those methods from a given input set which match the given source and target type of a mapping
 * and optionally other given criteria. An error will be raised if either no or more than one matching method are left
 * over after applying all selectors.
 *
 * @author Sjaak Derksen
 */
public interface MethodSelector {

    /**
     * Selects those methods which match the given types and other criteria
     *
     * @param <T> either SourceMethod or BuiltInMethod
     * @param mappingMethod mapping method, defined in Mapper for which this selection is carried out
     * @param methods list of available methods
     * @param sourceType parameter type that should be matched
     * @param targetType return type that should be matched
     * @param criteria criteria used in the selection process
     *
     * @return list of methods that passes the matching process
     */
    <T extends Method> List<T> getMatchingMethods(Method mappingMethod, List<T> methods, Type sourceType,
                                                  Type targetType, SelectionCriteria criteria);
}
