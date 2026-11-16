/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.util.ElementUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.TypeUtils;

/**
 * Applies all known {@link MethodSelector}s in order.
 *
 * @author Sjaak Derksen
 */
public class MethodSelectors {

    private final List<MethodSelector> selectors;

    public MethodSelectors(TypeUtils typeUtils, ElementUtils elementUtils,
                           FormattingMessager messager, Options options) {
        List<MethodSelector> selectorList = new ArrayList<>( Arrays.asList(
            new MethodFamilySelector(),
            new TypeSelector( messager ),
            new QualifierSelector( typeUtils, elementUtils ),
            new TargetTypeSelector( typeUtils ),
            new JavaxXmlElementDeclSelector( typeUtils ),
            new JakartaXmlElementDeclSelector( typeUtils ),
            new InheritanceSelector()
        ) );
        if ( options != null && !options.isDisableLifecycleOverloadDeduplicateSelector() ) {
            selectorList.add( new LifecycleOverloadDeduplicateSelector() );
        }

        selectorList.addAll( Arrays.asList(
            new CreateOrUpdateSelector(),
            new SourceRhsSelector(),
            new FactoryParameterSelector(),
            new MostSpecificResultTypeSelector()
        ) );
        this.selectors = selectorList;
    }

    /**
     * Selects those methods which match the given types and other criteria
     *
     * @param <T> either SourceMethod or BuiltInMethod
     * @param methods list of available methods
     * @param context the selection context that should be used in the matching process
     * @return list of methods that passes the matching process
     */
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(List<T> methods,
                                                                         SelectionContext context) {

        List<SelectedMethod<T>> candidates = new ArrayList<>( methods.size() );
        for ( T method : methods ) {
            candidates.add( new SelectedMethod<>( method ) );
        }

        for ( MethodSelector selector : selectors ) {
            candidates = selector.getMatchingMethods( candidates, context );
        }
        return candidates;
    }
}
