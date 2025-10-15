/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(List<SelectedMethod<T>> methods,
                                                                         SelectionContext context) {

        Type sourceType = context.getSourceType();
        if ( sourceType == null ) {
            return methods;
        }

        List<SelectedMethod<T>> candidatesWithBestMatchingSourceType = new ArrayList<>();
        int bestMatchingSourceTypeDistance = Integer.MAX_VALUE;

        // Find methods with the minimum inheritance distance from the source parameter type
        for ( SelectedMethod<T> method : methods ) {
            Parameter singleSourceParam = first( method.getMethod().getSourceParameters() );

            int sourceTypeDistance = sourceType.distanceTo( singleSourceParam.getType() );
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

    private <T extends Method> int addToCandidateListIfMinimal(List<SelectedMethod<T>> candidatesWithBestMatchingType,
                                                               int bestMatchingTypeDistance, SelectedMethod<T> method,
                                                               int currentTypeDistance) {
        if ( currentTypeDistance == bestMatchingTypeDistance ) {
            candidatesWithBestMatchingType.add( method );
        }
        else if ( currentTypeDistance < bestMatchingTypeDistance ) {
            bestMatchingTypeDistance = currentTypeDistance;

            candidatesWithBestMatchingType.clear();
            candidatesWithBestMatchingType.add( method );
        }
        return bestMatchingTypeDistance;
    }
}
