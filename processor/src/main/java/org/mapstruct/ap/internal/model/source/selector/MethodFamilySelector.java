/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.internal.gem.ConditionStrategyGem;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * Selects those methods from the given input set which match for the requested family of methods: factory methods,
 * lifecycle callback methods, or any other mapping methods.
 *
 * @author Remo Meier
 */
public class MethodFamilySelector implements MethodSelector {

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(List<SelectedMethod<T>> methods,
                                                                         SelectionContext context) {
        SelectionCriteria criteria = context.getSelectionCriteria();

        List<SelectedMethod<T>> result = new ArrayList<>( methods.size() );
        for ( SelectedMethod<T> method : methods ) {
            if ( criteria.isPresenceCheckRequired() ) {
                if ( method.getMethod()
                    .getConditionOptions()
                    .isStrategyApplicable( ConditionStrategyGem.PROPERTIES ) ) {
                    result.add( method );
                }
            }
            else if ( criteria.isSourceParameterCheckRequired() ) {
                if ( method.getMethod()
                    .getConditionOptions()
                    .isStrategyApplicable( ConditionStrategyGem.SOURCE_PARAMETERS ) ) {
                    result.add( method );
                }
            }
            else if ( method.getMethod().isObjectFactory() == criteria.isObjectFactoryRequired()
                && method.getMethod().isLifecycleCallbackMethod() == criteria.isLifecycleCallbackRequired()
            ) {

                result.add( method );
            }
        }
        return result;
    }
}
