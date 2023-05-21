/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * @author Filip Hrisafov
 */
public class MostSpecificResultTypeSelector implements MethodSelector {

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(List<SelectedMethod<T>> candidates,
                                                                         SelectionContext context) {
        SelectionCriteria criteria = context.getSelectionCriteria();
        Type mappingTargetType = context.getMappingTargetType();
        if ( candidates.size() < 2 || !criteria.isForMapping() || criteria.getQualifyingResultType() != null) {
            return candidates;
        }

        List<SelectedMethod<T>> result = new ArrayList<>();

        for ( SelectedMethod<T> candidate : candidates ) {
            if ( candidate.getMethod()
                .getResultType()
                .getBoxedEquivalent()
                .equals( mappingTargetType.getBoxedEquivalent() ) ) {
                // If the result type is the same as the target type
                // then this candidate has the most specific match and should be used
                result.add( candidate );
            }
        }


        // If not most specific types were found then return the current candidates
        return result.isEmpty() ? candidates : result;
    }
}
