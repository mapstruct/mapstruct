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

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.MethodMatcher;

/**
 * Selects those methods from the given input set which match the given source and target types (via
 * {@link MethodMatcher}).
 *
 * @author Sjaak Derksen
 */
public class TypeSelector implements MethodSelector {

    private final TypeFactory typeFactory;

    public TypeSelector(TypeFactory typeFactory) {
        this.typeFactory = typeFactory;
    }

    @Override
    public <T extends Method> List<T> getMatchingMethods(Method mappingMethod, List<T> methods, Type parameterType,
                                                         Type returnType, String targetPropertyName) {

        List<T> result = new ArrayList<T>();
        for ( T method : methods ) {
            if ( method.getSourceParameters().size() != 1 ) {
                continue;
            }

            List<Type> parameterTypes =
                MethodSelectors.getParameterTypes( typeFactory, method.getParameters(), parameterType, returnType );
            if ( method.matches( parameterTypes, returnType ) ) {
                result.add( method );
            }
        }
        return result;
    }
}
