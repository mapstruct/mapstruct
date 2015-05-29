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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.MethodMatcher;

/**
 * Selects those methods from the given input set which match the given source and target types (via
 * {@link MethodMatcher}).
 *
 * @author Sjaak Derksen
 */
public class TypeSelector implements MethodSelector {

    @Override
    public <T extends Method> List<T> getMatchingMethods(Method mappingMethod, List<T> methods,
                                                         Type sourceType, Type targetType,
                                                         SelectionCriteria criteria) {

        List<T> result = new ArrayList<T>();
        for ( T method : methods ) {
            if ( !method.isLifecycleCallbackMethod() && method.matches( Arrays.asList( sourceType ), targetType ) ) {
                result.add( method );
            }
        }
        return result;
    }
}
