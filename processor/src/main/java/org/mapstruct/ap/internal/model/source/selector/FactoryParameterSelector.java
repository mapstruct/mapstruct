/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.internal.model.source.Method;

/**
 * For factory methods, the candidate list is checked if it contains a method with a source parameter which is to be
 * favored compared to factory methods without a source parameter. It returns the original list of candidates in case of
 * ambiguities.
 *
 * @author Andreas Gudian
 */
public class FactoryParameterSelector implements MethodSelector {

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(List<SelectedMethod<T>> methods,
                                                                         SelectionContext context) {
        SelectionCriteria criteria = context.getSelectionCriteria();
        if ( !criteria.isObjectFactoryRequired() || methods.size() <= 1 ) {
            return methods;
        }

        List<SelectedMethod<T>> sourceParamFactoryMethods = new ArrayList<>( methods.size() );

        for ( SelectedMethod<T> candidate : methods ) {
            if ( !candidate.getMethod().getSourceParameters().isEmpty() ) {
                sourceParamFactoryMethods.add( candidate );
            }
        }

        if ( sourceParamFactoryMethods.size() == 1 ) {
            // there is exactly one candidate with source params, so favor that one.
            return sourceParamFactoryMethods;
        }

        // let the caller produce an ambiguity error referencing all possibly matching methods
        return methods;
    }
}
