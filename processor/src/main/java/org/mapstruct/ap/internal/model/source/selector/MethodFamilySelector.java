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
 * Selects those methods from the given input set which match for the requested family of methods: factory methods,
 * lifecycle callback methods, or any other mapping methods.
 *
 * @author Remo Meier
 */
public class MethodFamilySelector implements MethodSelector {

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method mappingMethod,
                                                                          List<SelectedMethod<T>> methods,
                                                                          List<Type> sourceTypes,
                                                                          Type targetType, SelectionCriteria criteria) {

        List<SelectedMethod<T>> result = new ArrayList<SelectedMethod<T>>( methods.size() );
        for ( SelectedMethod<T> method : methods ) {
            if ( method.getMethod().isObjectFactory() == criteria.isObjectFactoryRequired()
                && method.getMethod().isLifecycleCallbackMethod() == criteria.isLifecycleCallbackRequired() ) {

                result.add( method );
            }
        }
        return result;
    }
}
