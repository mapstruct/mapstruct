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
 * Selects those methods from the given input set which match for the requested family of methods: factory methods,
 * lifecycle callback methods, or any other mapping methods.
 *
 * @author Remo Meier
 */
public class MethodFamilySelector implements MethodSelector {

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method mappingMethod,
                                                                          List<SelectedMethod<T>> methods,
                                                                          List<Type> sourceTypes,
                                                                          Type targetType, SelectionCriteria criteria) {

        List<SelectedMethod<T>> result = new ArrayList<SelectedMethod<T>>( methods.size() );
        for ( SelectedMethod<T> method : methods ) {
            if ( method.getMethod().isObjectFactory() == criteria.isObjectFactoryRequired()
                && method.getMethod().isLifecycleCallbackMethod() == criteria.isLifecycleCallbackRequired() ) {

                result.add( method );
            }
        }
        return result;
    }
}
