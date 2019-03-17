/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import static org.mapstruct.ap.internal.util.Collections.first;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

/**
 * Builder that can be used to build {@link ContainerMappingMethod}(s).
 *
 * @param <B> the builder itself that needs to be used for chaining
 * @param <M> the method that the builder builds
 *
 * @author Filip Hrisafov
 */
public abstract class ContainerMappingMethodBuilder<B extends ContainerMappingMethodBuilder<B, M>,
    M extends ContainerMappingMethod> extends AbstractMappingMethodBuilder<B, M> {

    private SelectionParameters selectionParameters;
    private FormattingParameters formattingParameters;
    private NullValueMappingStrategyPrism nullValueMappingStrategy;
    private String errorMessagePart;
    private String callingContextTargetPropertyName;

    ContainerMappingMethodBuilder(Class<B> selfType, String errorMessagePart) {
        super( selfType );
        this.errorMessagePart = errorMessagePart;
    }

    public B formattingParameters(FormattingParameters formattingParameters) {
        this.formattingParameters = formattingParameters;
        return myself;
    }

    public B selectionParameters(SelectionParameters selectionParameters) {
        this.selectionParameters = selectionParameters;
        return myself;
    }

    public B nullValueMappingStrategy(NullValueMappingStrategyPrism nullValueMappingStrategy) {
        this.nullValueMappingStrategy = nullValueMappingStrategy;
        return myself;
    }

    public B callingContextTargetPropertyName(String callingContextTargetPropertyName) {
        this.callingContextTargetPropertyName = callingContextTargetPropertyName;
        return myself;
    }

    @Override
    public final M build() {
        Type sourceParameterType = first( method.getSourceParameters() ).getType();
        Type resultType = method.getResultType();

        Type sourceElementType = getElementType( sourceParameterType );
        Type targetElementType = getElementType( resultType );

        String loopVariableName =
            Strings.getSafeVariableName( sourceElementType.getName(), method.getParameterNames() );

        SourceRHS sourceRHS = new SourceRHS(
            loopVariableName,
            sourceElementType,
            new HashSet<>(),
            errorMessagePart
        );
        Assignment assignment = ctx.getMappingResolver().getTargetAssignment(
            method,
            targetElementType,
            callingContextTargetPropertyName,
            formattingParameters,
            selectionParameters,
            sourceRHS,
            false,
            null
        );

        if ( assignment == null ) {
            assignment = forgeMapping( sourceRHS, sourceElementType, targetElementType );
            if ( assignment != null ) {
                ctx.getMessager().note( 2, Message.ITERABLEMAPPING_CREATE_ELEMENT_NOTE, assignment );
            }
        }
        else {
            ctx.getMessager().note( 2, Message.ITERABLEMAPPING_SELECT_ELEMENT_NOTE, assignment );
        }

        if ( assignment == null ) {
            if ( method instanceof ForgedMethod ) {
                // leave messaging to calling property mapping
                return null;
            }
            else {
                reportCannotCreateMapping(
                    method,
                    String.format( "%s \"%s\"", sourceRHS.getSourceErrorMessagePart(), sourceRHS.getSourceType() ),
                    sourceRHS.getSourceType(),
                    targetElementType,
                    ""
                );
            }
        }
        else {
            if ( method instanceof ForgedMethod ) {
                ForgedMethod forgedMethod = (ForgedMethod) method;
                forgedMethod.addThrownTypes( assignment.getThrownTypes() );
            }
        }
        assignment = getWrapper( assignment, method );

        // mapNullToDefault
        boolean mapNullToDefault = false;
        if ( method.getMapperConfiguration() != null ) {
            mapNullToDefault = method.getMapperConfiguration().isMapToDefault( nullValueMappingStrategy );
        }

        MethodReference factoryMethod = null;
        if ( !method.isUpdateMethod() ) {
            factoryMethod = ObjectFactoryMethodResolver.getFactoryMethod( method, method.getResultType(), null, ctx );
        }

        Set<String> existingVariables = new HashSet<>( method.getParameterNames() );
        existingVariables.add( loopVariableName );

        List<LifecycleCallbackMethodReference> beforeMappingMethods = LifecycleMethodResolver.beforeMappingMethods(
            method,
            selectionParameters,
            ctx,
            existingVariables
        );
        List<LifecycleCallbackMethodReference> afterMappingMethods = LifecycleMethodResolver.afterMappingMethods(
            method,
            selectionParameters,
            ctx,
            existingVariables
        );

        return instantiateMappingMethod(
            method,
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

    protected abstract M instantiateMappingMethod(Method method, Collection<String> existingVariables,
                                                  Assignment assignment, MethodReference factoryMethod,
                                                  boolean mapNullToDefault, String loopVariableName,
                                                  List<LifecycleCallbackMethodReference> beforeMappingMethods,
                                                  List<LifecycleCallbackMethodReference> afterMappingMethods,
        SelectionParameters selectionParameters);

    protected abstract Type getElementType(Type parameterType);

    protected abstract Assignment getWrapper(Assignment assignment, Method method);

    @Override
    protected boolean shouldUsePropertyNamesInHistory() {
        return false;
    }
}
