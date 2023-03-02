/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * Maps from a source to a target where one or the other (or both) are an {@link Optional} type.
 *
 * @author Ken Wang
 */
public class OptionalMappingMethod extends ContainerMappingMethod {

    public static class Builder extends ContainerMappingMethodBuilder<Builder, OptionalMappingMethod> {

        public Builder() {
            super( Builder.class, "optional element" );
        }

        @Override
        protected Type getElementType(Type parameterType) {
            if ( parameterType.isOptionalType() ) {
                return parameterType.getTypeParameters().get( 0 );
            }
            else {
                return parameterType;
            }
        }

        @Override
        protected Assignment getWrapper(Assignment assignment, Method method) {
            return assignment;
        }

        @Override
        protected OptionalMappingMethod instantiateMappingMethod(Method method, Collection<String> existingVariables,
            Assignment assignment, MethodReference factoryMethod, boolean mapNullToDefault, String loopVariableName,
            List<LifecycleCallbackMethodReference> beforeMappingMethods,
            List<LifecycleCallbackMethodReference> afterMappingMethods, SelectionParameters selectionParameters) {
            return new OptionalMappingMethod(
                    method,
                    getMethodAnnotations(),
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

    private OptionalMappingMethod(Method method,
                                  List<Annotation> annotations,
                                  Collection<String> existingVariables,
                                  Assignment parameterAssignment,
                                  MethodReference factoryMethod,
                                  boolean mapNullToDefault,
                                  String loopVariableName,
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

        if ( sourceParameterType.isOptionalType() ) {
            return first( sourceParameterType.determineTypeArguments( Optional.class ) ).getTypeBound();
        }
        else {
            return sourceParameterType;
        }
    }

    @Override
    public Type getResultElementType() {
        Type resultParameterType = getResultType();

        if ( resultParameterType.isOptionalType() ) {
            return first( resultParameterType.determineTypeArguments( Optional.class ) );
        }
        else {
            return resultParameterType;
        }
    }
}
