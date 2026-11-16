/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * Selector for deduplicating overloaded lifecycle callback methods
 * whose parameter signatures differ only by type hierarchy.
 * <p>
 * In the context of lifecycle callback method selection
 * (such as @BeforeMapping or @AfterMapping), it is possible to have multiple overloaded methods
 * whose parameter lists are structurally identical except for the specific types,
 * where those types are related by inheritance (e.g., one parameter is a superclass or subclass of another).
 * <p>
 * This selector groups such methods by their effective parameter signature
 * (ignoring differences only in type hierarchy), and, within each group,
 * retains only the method whose parameter types have the closest inheritance distance
 * to the actual invocation types.
 * This ensures that, for each group of nearly identical overloads,
 * only the most specific and appropriate method is selected.
 * <p>
 * <b>Example (see Issue3849Test):</b>
 *
 * <pre>{@code
 * @AfterMapping
 * default void afterMapping(Parent source, @MappingTarget ParentDto target) { ... }
 * @AfterMapping
 * default void afterMapping(Parent source, @MappingTarget ChildDto target) { ... }
 * }</pre>
 * When mapping a Child to a ChildDto,
 * only the method with ChildDto is selected, even though both methods match by signature
 * except for the target type's inheritance relationship.
 */
public class LifecycleOverloadDeduplicateSelector implements MethodSelector {
    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(List<SelectedMethod<T>> methods,
                                                                         SelectionContext context) {
        if ( !context.getSelectionCriteria().isLifecycleCallbackRequired() || methods.size() <= 1 ) {
            return methods;
        }
        Collection<List<SelectedMethod<T>>> methodSignatureGroups =
            methods.stream()
                .collect( Collectors.groupingBy(
                    LifecycleOverloadDeduplicateSelector::buildSignatureKey,
                    LinkedHashMap::new,
                    Collectors.toList()
                ) )
                .values();
        List<SelectedMethod<T>> deduplicatedMethods = new ArrayList<>( methods.size() );
        for ( List<SelectedMethod<T>> signatureGroup : methodSignatureGroups ) {
            if ( signatureGroup.size() == 1 ) {
                deduplicatedMethods.add( signatureGroup.get( 0 ) );
                continue;
            }
            SelectedMethod<T> bestInheritanceMethod = signatureGroup.get( 0 );
            for ( int i = 1; i < signatureGroup.size(); i++ ) {
                SelectedMethod<T> candidateMethod = signatureGroup.get( i );
                if ( isInheritanceBetter( candidateMethod, bestInheritanceMethod ) ) {
                    bestInheritanceMethod = candidateMethod;
                }
            }
            deduplicatedMethods.add( bestInheritanceMethod );
        }
        return deduplicatedMethods;
    }

    /**
     * Builds a grouping key for a method based on its defining type,
     * method name, and a detailed breakdown of each parameter binding.
     * <p>
     * The key consists of:
     * <ul>
     *   <li>The type that defines the method</li>
     *   <li>The method name</li>
     *   <li>parameter bindings</li>
     * </ul>
     * This ensures that methods are grouped together only if all these aspects match,
     * except for differences in type hierarchy, which are handled separately.
     */
    private static <T extends Method> List<Object> buildSignatureKey(SelectedMethod<T> method) {
        List<Object> key = new ArrayList<>();
        key.add( method.getMethod().getDefiningType() );
        key.add( method.getMethod().getName() );
        for ( ParameterBinding binding : method.getParameterBindings() ) {
            key.add( binding.getType() );
            key.add( binding.getVariableName() );
        }
        return key;
    }

    /**
     * Compare the inheritance distance of parameters between two methods to determine if candidateMethod is better.
     * Compares parameters in order, returns as soon as a better one is found.
     */
    private <T extends Method> boolean isInheritanceBetter(SelectedMethod<T> candidateMethod,
                                                           SelectedMethod<T> currentBestMethod) {
        List<ParameterBinding> candidateBindings = candidateMethod.getParameterBindings();
        List<ParameterBinding> bestBindings = currentBestMethod.getParameterBindings();
        List<Parameter> candidateParams = candidateMethod.getMethod().getParameters();
        List<Parameter> bestParams = currentBestMethod.getMethod().getParameters();
        int paramCount = candidateBindings.size();

        for ( int i = 0; i < paramCount; i++ ) {
            int candidateDistance = candidateBindings.get( i )
                .getType()
                .distanceTo( candidateParams.get( i ).getType() );
            int bestDistance = bestBindings.get( i ).getType().distanceTo( bestParams.get( i ).getType() );
            if ( candidateDistance < bestDistance ) {
                return true;
            }
            else if ( candidateDistance > bestDistance ) {
                return false;
            }
        }
        return false;
    }
}
