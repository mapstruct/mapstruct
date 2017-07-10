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
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * For factory methods, the candidate list is checked if it contains a method with a source parameter which is to be
 * favored compared to factory methods without a source parameter. It returns the original list of candidates in case of
 * ambiguities.
 *
 * @author Andreas Gudian
 */
public class FactoryParameterSelector implements MethodSelector {

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method mappingMethod,
            List<SelectedMethod<T>> methods,
            List<Type>sourceTypes,
            Type targetType,
            SelectionCriteria criteria) {
        if ( !criteria.isObjectFactoryRequired() || methods.size() <= 1 ) {
            return methods;
        }

        List<SelectedMethod<T>> sourceParamFactoryMethods = new ArrayList<SelectedMethod<T>>( methods.size() );

        for ( SelectedMethod<T> candidate : methods ) {
            if ( !candidate.getMethod().getSourceParameters().isEmpty() ) {
                sourceParamFactoryMethods.add( candidate );
            }
        }

        if ( sourceParamFactoryMethods.size() == 1 ) {
            // there is exactly one candidate with source params, so favor that one.
            return sourceParamFactoryMethods;
        }

        // let the caller produce an ambiguity error referencing all possibly matching methods
        return methods;
    }
}
