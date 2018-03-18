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
import java.util.Arrays;
import java.util.List;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * Applies all known {@link MethodSelector}s in order.
 *
 * @author Sjaak Derksen
 */
public class MethodSelectors {

    private final List<MethodSelector> selectors;

    public MethodSelectors(Types typeUtils, Elements elementUtils, TypeFactory typeFactory) {
        selectors = Arrays.asList(
            new MethodFamilySelector(),
            new TypeSelector( typeFactory ),
            new QualifierSelector( typeUtils, elementUtils ),
            new TargetTypeSelector( typeUtils, elementUtils ),
            new XmlElementDeclSelector( typeUtils ),
            new InheritanceSelector(),
            new CreateOrUpdateSelector(),
            new FactoryParameterSelector() );
    }

    /**
     * Selects those methods which match the given types and other criteria
     *
     * @param <T> either SourceMethod or BuiltInMethod
     * @param mappingMethod mapping method, defined in Mapper for which this selection is carried out
     * @param methods list of available methods
     * @param sourceTypes parameter type(s) that should be matched
     * @param targetType return type that should be matched
     * @param criteria criteria used in the selection process
     * @return list of methods that passes the matching process
     */
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method mappingMethod, List<T> methods,
                                                                          List<Type> sourceTypes, Type targetType,
                                                                          SelectionCriteria criteria) {

        List<SelectedMethod<T>> candidates = new ArrayList<SelectedMethod<T>>( methods.size() );
        for ( T method : methods ) {
            candidates.add( new SelectedMethod<T>( method ) );
        }

        for ( MethodSelector selector : selectors ) {
            candidates = selector.getMatchingMethods(
                mappingMethod,
                candidates,
                sourceTypes,
                targetType,
                criteria );
        }
        return candidates;
    }
}
