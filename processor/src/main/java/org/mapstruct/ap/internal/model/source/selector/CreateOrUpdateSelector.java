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
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(List<SelectedMethod<T>> methods,
                                                                         SelectionContext context) {
        SelectionCriteria criteria = context.getSelectionCriteria();
        if ( criteria.isLifecycleCallbackRequired() || criteria.isObjectFactoryRequired()
            || criteria.isSourceParameterCheckRequired()
            || criteria.isPresenceCheckRequired() ) {
            return methods;
        }

        List<SelectedMethod<T>> createCandidates = new ArrayList<>();
        List<SelectedMethod<T>> updateCandidates = new ArrayList<>();
        for ( SelectedMethod<T> method : methods ) {
            boolean isCreateCandidate = method.getMethod().getMappingTargetParameter() == null;
            if ( isCreateCandidate ) {
                createCandidates.add( method );
            }
            else {
                updateCandidates.add( method );
            }
        }
        if ( criteria.isPreferUpdateMapping() && !updateCandidates.isEmpty() ) {
            return updateCandidates;
        }
        return createCandidates;
    }
}
