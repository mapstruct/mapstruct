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
package org.mapstruct.ap.model.source.selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.model.source.Method;

/**
 * Applies all known {@link MethodSelector}s in order.
 *
 * @author Sjaak Derksen
 */
public class MethodSelectors implements MethodSelector {

    private final List<MethodSelector> selectors;

    public MethodSelectors(Types typeUtils, Elements elementUtils, TypeFactory typeFactory) {
        selectors =
            Arrays.<MethodSelector>asList(
                new TypeSelector( typeFactory ),
                new QualifierSelector( typeUtils, elementUtils ),
                new TargetTypeSelector( typeUtils, elementUtils ),
                new XmlElementDeclSelector( typeUtils ),
                new InheritanceSelector()
            );
    }

    @Override
    public <T extends Method> List<T> getMatchingMethods(Method mappingMethod, List<T> methods,
                                                         Type sourceType, Type targetType,
                                                         SelectionCriteria criteria) {

        List<T> candidates = new ArrayList<T>( methods );

        for ( MethodSelector selector : selectors ) {
            candidates = selector.getMatchingMethods(
                mappingMethod,
                candidates,
                sourceType,
                targetType,
                criteria
            );
        }
        return candidates;
    }

    /**
     * @param typeFactory the type factory to use
     * @param parameters the parameters to map the types for
     * @param sourceType the source type
     * @param returnType the return type
     *
     * @return the list of actual parameter types
     */
    public static List<Type> getParameterTypes(TypeFactory typeFactory, List<Parameter> parameters, Type sourceType,
                                               Type returnType) {
        List<Type> result = new ArrayList<Type>();
        for ( Parameter param : parameters ) {
            if ( param.isTargetType() ) {
                result.add( typeFactory.classTypeOf( returnType ) );
            }
            else {
                if ( sourceType != null ) {
                    /* for factory methods (sourceType==null), no parameter must be added */
                    result.add( sourceType );
                }
            }
        }

        return result;
    }
}
