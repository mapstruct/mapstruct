/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.List;

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
     * @param candidates list of available methods
     * @param context the context for the matching
     * @return list of methods that passes the matching process
     */
    <T extends Method> List<SelectedMethod<T>> getMatchingMethods(List<SelectedMethod<T>> candidates,
                                                                  SelectionContext context);
}
