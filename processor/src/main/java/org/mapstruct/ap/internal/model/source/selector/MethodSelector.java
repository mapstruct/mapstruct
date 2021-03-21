/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.List;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * Implementations select those methods from a given input set which match the given source and target type of a mapping
 * and optionally other given criteria. An error will be raised if either no or more than one matching method are left
 * over after applying all selectors.
 *
 * @author Sjaak Derksen
 */
interface MethodSelector {

    /**
     * Selects those methods which match the given types and other criteria
     *
     * @param <T> either SourceMethod or BuiltInMethod
     * @param mappingMethod mapping method, defined in Mapper for which this selection is carried out
     * @param candidates list of available methods
     * @param sourceTypes parameter type(s) that should be matched
     * @param mappingTargetType mappingTargetType that should be matched
     * @param returnType return type that should be matched
     * @param criteria criteria used in the selection process
     * @return list of methods that passes the matching process
     */
    <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method mappingMethod,
                                                                  List<SelectedMethod<T>> candidates,
                                                                  List<Type> sourceTypes,
                                                                  Type mappingTargetType,
                                                                  Type returnType,
                                                                  SelectionCriteria criteria);
}
