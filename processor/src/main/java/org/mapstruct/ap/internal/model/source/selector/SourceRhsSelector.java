/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * Selector that tries to resolve an ambiguity between methods that contain source parameters and
 * {@link org.mapstruct.ap.internal.model.common.SourceRHS SourceRHS} type parameters.
 * @author Filip Hrisafov
 */
public class SourceRhsSelector implements MethodSelector {

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(List<SelectedMethod<T>> candidates,
                                                                         SelectionContext context) {
        SelectionCriteria criteria = context.getSelectionCriteria();
        if ( candidates.size() < 2 || criteria.getSourceRHS() == null ) {
            return candidates;
        }

        List<SelectedMethod<T>> sourceRHSFavoringCandidates = new ArrayList<>();

        for ( SelectedMethod<T> candidate : candidates ) {
            for ( ParameterBinding parameterBinding : candidate.getParameterBindings() ) {
                if ( parameterBinding.getSourceRHS() != null ) {
                    sourceRHSFavoringCandidates.add( candidate );
                    break;
                }
            }

        }

        if ( !sourceRHSFavoringCandidates.isEmpty() ) {
            return sourceRHSFavoringCandidates;
        }

        return candidates;
    }
}
