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

import static org.mapstruct.ap.internal.util.Collections.first;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * Selects on inheritance distance, e.g. the amount of inheritance steps from the parameter type.
 *
 * @author Sjaak Derksen
 */
public class InheritanceSelector implements MethodSelector {

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method mappingMethod,
                                                                          List<SelectedMethod<T>> methods,
                                                                          List<Type> sourceTypes,
                                                                          Type targetType,
                                                                          SelectionCriteria criteria) {

        if ( sourceTypes.size() != 1 ) {
            return methods;
        }

        Type singleSourceType = first( sourceTypes );

        List<SelectedMethod<T>> candidatesWithBestMatchingSourceType = new ArrayList<SelectedMethod<T>>();
        int bestMatchingSourceTypeDistance = Integer.MAX_VALUE;

        // find the methods with the minimum distance regarding getParameter getParameter type
        for ( SelectedMethod<T> method : methods ) {
            Parameter singleSourceParam = first( method.getMethod().getSourceParameters() );

            int sourceTypeDistance = singleSourceType.distanceTo( singleSourceParam.getType() );
            bestMatchingSourceTypeDistance =
                addToCandidateListIfMinimal(
                    candidatesWithBestMatchingSourceType,
                    bestMatchingSourceTypeDistance,
                    method,
                    sourceTypeDistance
                );
        }
        return candidatesWithBestMatchingSourceType;
    }

    private <T extends Method> int addToCandidateListIfMinimal(List<SelectedMethod<T>> candidatesWithBestMathingType,
                                                               int bestMatchingTypeDistance, SelectedMethod<T> method,
                                                               int currentTypeDistance) {
        if ( currentTypeDistance == bestMatchingTypeDistance ) {
            candidatesWithBestMathingType.add( method );
        }
        else if ( currentTypeDistance < bestMatchingTypeDistance ) {
            bestMatchingTypeDistance = currentTypeDistance;

            candidatesWithBestMathingType.clear();
            candidatesWithBestMathingType.add( method );
        }
        return bestMatchingTypeDistance;
    }
}
