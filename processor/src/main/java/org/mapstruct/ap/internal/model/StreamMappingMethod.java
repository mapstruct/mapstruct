/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import org.mapstruct.ap.internal.model.assignment.Java8FunctionWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one iterable or array type to Stream.
 * The collection elements are mapped either by a {@link TypeConversion} or another mapping method.
 *
 * @author Filip Hrisafov
 */
public class StreamMappingMethod extends ContainerMappingMethod {

    private final Set<Type> helperImports;

    public static class Builder extends ContainerMappingMethodBuilder<Builder, StreamMappingMethod> {

        public Builder() {
            super( Builder.class, "stream element" );
        }

        @Override
        protected Type getElementType(Type parameterType) {
            return StreamMappingMethod.getElementType( parameterType );
        }

        @Override
        protected Assignment getWrapper(Assignment assignment, Method method) {
            return new Java8FunctionWrapper( assignment );
        }

        @Override
        protected StreamMappingMethod instantiateMappingMethod(Method method, Collection<String> existingVariables,
            Assignment assignment, MethodReference factoryMethod, boolean mapNullToDefault, String loopVariableName,
            List<LifecycleCallbackMethodReference> beforeMappingMethods,
            List<LifecycleCallbackMethodReference> afterMappingMethods, SelectionParameters selectionParameters) {

            Set<Type> helperImports = new HashSet<>();
            if ( method.getResultType().isIterableType() ) {
                helperImports.add( ctx.getTypeFactory().getType( Collectors.class ) );
            }

            Type sourceParameterType = first( method.getSourceParameters() ).getType();
            if ( !sourceParameterType.isCollectionType() && !sourceParameterType.isArrayType() &&
                sourceParameterType.isIterableType() ) {
                helperImports.add( ctx.getTypeFactory().getType( StreamSupport.class ) );
            }
            return new StreamMappingMethod(
                method,
                getMethodAnnotations(),
                existingVariables,
                assignment,
                factoryMethod,
                mapNullToDefault,
                loopVariableName,
                beforeMappingMethods,
                afterMappingMethods,
                selectionParameters,
                helperImports
            );
        }
    }
    //CHECKSTYLE:OFF
    private StreamMappingMethod(Method method, List<Annotation> annotations,
                                Collection<String> existingVariables, Assignment parameterAssignment,
                                MethodReference factoryMethod, boolean mapNullToDefault, String loopVariableName,
                                List<LifecycleCallbackMethodReference> beforeMappingReferences,
                                List<LifecycleCallbackMethodReference> afterMappingReferences,
        SelectionParameters selectionParameters, Set<Type> helperImports) {
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
        this.helperImports = helperImports;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();

        types.addAll( helperImports );

        return types;
    }

    public Type getSourceElementType() {
        return getElementType( getSourceParameter().getType() );
    }

    public Type getResultElementType() {
        return getElementType( getResultType() );
    }

    private static Type getElementType(Type parameterType) {
        if ( parameterType.isArrayType() ) {
            return parameterType.getComponentType();
        }
        else if ( parameterType.isIterableType() ) {
            return first( parameterType.determineTypeArguments( Iterable.class ) ).getTypeBound();
        }
        else if ( parameterType.isStreamType() ) {
            return first( parameterType.determineTypeArguments( Stream.class ) ).getTypeBound();
        }

        throw new IllegalArgumentException( "Could not get the element type" );
    }
}
