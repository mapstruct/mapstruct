/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.FormattingMessager;

/**
 * Applies all known {@link MethodSelector}s in order.
 *
 * @author Sjaak Derksen
 */
public class MethodSelectors {

    private final List<MethodSelector> selectors;

    public MethodSelectors(Types typeUtils, Elements elementUtils, TypeFactory typeFactory,
                           FormattingMessager messager) {
        selectors = Arrays.asList(
            new MethodFamilySelector(),
            new TypeSelector( typeFactory, messager ),
            new QualifierSelector( typeUtils, elementUtils ),
            new TargetTypeSelector( typeUtils, elementUtils ),
            new XmlElementDeclSelector( typeUtils ),
            new InheritanceSelector(),
            new CreateOrUpdateSelector(),
            new FactoryParameterSelector() );
    }

    /**
     * Selects those methods which match the given types and other criteria
     *
     * @param <T> either SourceMethod or BuiltInMethod
     * @param mappingMethod mapping method, defined in Mapper for which this selection is carried out
     * @param methods list of available methods
     * @param sourceTypes parameter type(s) that should be matched
     * @param targetType return type that should be matched
     * @param criteria criteria used in the selection process
     * @return list of methods that passes the matching process
     */
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method mappingMethod, List<T> methods,
                                                                          List<Type> sourceTypes, Type targetType,
                                                                          SelectionCriteria criteria) {

        List<SelectedMethod<T>> candidates = new ArrayList<>( methods.size() );
        for ( T method : methods ) {
            candidates.add( new SelectedMethod<>( method ) );
        }

        for ( MethodSelector selector : selectors ) {
            candidates = selector.getMatchingMethods(
                mappingMethod,
                candidates,
                sourceTypes,
                targetType,
                criteria );
        }
        return candidates;
    }
}
