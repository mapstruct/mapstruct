/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import static org.mapstruct.ap.internal.util.Collections.first;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.assignment.LocalVarWrapper;
import org.mapstruct.ap.internal.model.assignment.SetterWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one iterable type to another. The collection
 * elements are mapped either by a {@link TypeConversion} or another mapping method.
 *
 * @author Gunnar Morling
 */
public class IterableMappingMethod extends ContainerMappingMethod {

    public static class Builder extends ContainerMappingMethodBuilder<Builder, IterableMappingMethod> {

        public Builder() {
            super( Builder.class, "collection element" );
        }

        @Override
        protected Type getElementType(Type parameterType) {
            return parameterType.isArrayType() ? parameterType.getComponentType() : first(
                parameterType.determineTypeArguments( Iterable.class ) ).getTypeBound();
        }

        @Override
        protected Assignment getWrapper(Assignment assignment, Method method) {
            Type resultType = method.getResultType();
            // target accessor is setter, so decorate assignment as setter
            if ( resultType.isArrayType() ) {
                return new LocalVarWrapper( assignment, method.getThrownTypes(), resultType, false );
            }
            else {
                return new SetterWrapper( assignment, method.getThrownTypes(), false );
            }
        }

        @Override
        protected IterableMappingMethod instantiateMappingMethod(Method method, Collection<String> existingVariables,
            Assignment assignment, MethodReference factoryMethod, boolean mapNullToDefault, String loopVariableName,
            List<LifecycleCallbackMethodReference> beforeMappingMethods,
            List<LifecycleCallbackMethodReference> afterMappingMethods, SelectionParameters selectionParameters) {
            return new IterableMappingMethod(
                method,
                getMethodAnnotation(),
                existingVariables,
                assignment,
                factoryMethod,
                mapNullToDefault,
                loopVariableName,
                beforeMappingMethods,
                afterMappingMethods,
                selectionParameters
            );
        }
    }

    private IterableMappingMethod(Method method, List<Annotation> annotations,
                                  Collection<String> existingVariables, Assignment parameterAssignment,
                                  MethodReference factoryMethod, boolean mapNullToDefault, String loopVariableName,
                                  List<LifecycleCallbackMethodReference> beforeMappingReferences,
                                  List<LifecycleCallbackMethodReference> afterMappingReferences,
        SelectionParameters selectionParameters) {
        super(
            method,
            annotations,
            existingVariables,
            parameterAssignment,
            factoryMethod,
            mapNullToDefault,
            loopVariableName,
            beforeMappingReferences,
            afterMappingReferences,
            selectionParameters
        );
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();

        types.add( getSourceElementType() );
        return types;
    }

    public Type getSourceElementType() {
        Type sourceParameterType = getSourceParameter().getType();

        if ( sourceParameterType.isArrayType() ) {
            return sourceParameterType.getComponentType();
        }
        else {
            return first( sourceParameterType.determineTypeArguments( Iterable.class ) ).getTypeBound();
        }
    }

    @Override
    public Type getResultElementType() {
        if ( getResultType().isArrayType() ) {
            return getResultType().getComponentType();
        }
        else {
            return first( getResultType().determineTypeArguments( Iterable.class ) );
        }
    }
}
