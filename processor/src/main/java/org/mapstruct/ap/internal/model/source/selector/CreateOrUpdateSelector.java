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
import java.util.List;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * Selection based on type of method (create - or update method).
 * <p>
 * Prefers (when present):
 * <ol>
 * <li>create method candidates (methods not containing {@code &#64;MappingTarget}) when mapping method is a create
 * method</li>
 * <li>update method candidates (methods containing {@code &#64;MappingTarget} ) when mapping method is an update method
 * </li>
 * </ol>
 * When not present, the remaining (createCandidates when mapping method is update method, updateCandidates when mapping
 * method is a create method) candidates are selected.
 *
 * @author Sjaak Derksen
 */
public class CreateOrUpdateSelector implements MethodSelector {

    @Override
    public <T extends Method> List<T> getMatchingMethods(Method mappingMethod, List<T> methods,
                                                         Type sourceType, Type targetType,
                                                         SelectionCriteria criteria) {

        boolean isCreateMethod = mappingMethod.getMappingTargetParameter() == null;
        List<T> createCandidates = new ArrayList<T>();
        List<T> updateCandidates = new ArrayList<T>();
        for ( T method : methods ) {
            boolean isCreateCandidate = method.getMappingTargetParameter() == null;
            if ( isCreateCandidate ) {
                createCandidates.add( method );
            }
            else {
                updateCandidates.add( method );
            }
        }
        if ( criteria.isPreferUpdateMapping() ) {
            if ( !updateCandidates.isEmpty() ) {
                return updateCandidates;
            }
        }
        return createCandidates;
    }
}
