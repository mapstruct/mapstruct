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
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.SourceMethod;

/**
 * This class provides the initial set of methods {@link MethodMatcher}
 * @author Sjaak Derksen
 */
public class InitialSelector implements MethodSelector {

    /**
     * {@inheritDoc} {@link MethodSelector}
     */
    @Override
    public <T extends Method> List<T> getMatchingMethods(
            SourceMethod mappingMethod,
            Iterable<T> methods,
            Type parameterType,
            Type returnType,
            String targetPropertyName
        ) {

        List<T> result = new ArrayList<T>();
        for ( T method : methods ) {
            if ( method.getSourceParameters().size() != 1 ) {
                continue;
            }

            if ( method.matches( parameterType, returnType ) ) {
                result.add( method );
            }
        }
        return result;
    }
}
