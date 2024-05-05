/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.version.VersionInformation;

import static org.mapstruct.ap.internal.util.Collections.first;

import javax.lang.model.element.AnnotationMirror;

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
    private String errorMessagePart;
    private String callingContextTargetPropertyName;
    private AnnotationMirror positionHint;
    private VersionInformation versionInformation;

    ContainerMappingMethodBuilder(Class<B> selfType, String errorMessagePart) {
        super( selfType );
        this.errorMessagePart = errorMessagePart;
    }

    public B formattingParameters(FormattingParameters formattingParameters) {
        this.formattingParameters = formattingParameters;
        return myself;
    }

    public B versionInformation(VersionInformation versionInformation) {
        this.versionInformation = versionInformation;
        return myself;
    }

    public B selectionParameters(SelectionParameters selectionParameters) {
        this.selectionParameters = selectionParameters;
        return myself;
    }

    public B callingContextTargetPropertyName(String callingContextTargetPropertyName) {
        this.callingContextTargetPropertyName = callingContextTargetPropertyName;
        return myself;
    }

    public B positionHint(AnnotationMirror positionHint) {
        this.positionHint = positionHint;
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

        SelectionCriteria criteria = SelectionCriteria.forMappingMethods( selectionParameters,
                        method.getOptions().getIterableMapping().getMappingControl( ctx.getElementUtils() ),
                        callingContextTargetPropertyName,
                        false
        );

        Assignment assignment = ctx.getMappingResolver().getTargetAssignment( method,
            getDescription(),
            targetElementType,
            formattingParameters,
            criteria,
            sourceRHS,
            positionHint,
            () -> forge( sourceRHS, sourceElementType, targetElementType )
        );

        if ( assignment == null ) {
            if ( method instanceof ForgedMethod ) {
                // leave messaging to calling property mapping
                return null;
            }
            else {
                reportCannotCreateMapping(
                    method,
                    String.format( "%s \"%s\"", sourceRHS.getSourceErrorMessagePart(),
                        sourceRHS.getSourceType().describe() ),
                    sourceRHS.getSourceType(),
                    targetElementType,
                    ""
                );
            }
        }
        else {
            ctx.getMessager().note( 2, Message.ITERABLEMAPPING_SELECT_ELEMENT_NOTE, assignment );
            if ( method instanceof ForgedMethod ) {
                ForgedMethod forgedMethod = (ForgedMethod) method;
                forgedMethod.addThrownTypes( assignment.getThrownTypes() );
            }
        }
        assignment = getWrapper( assignment, method );

        // mapNullToDefault
        boolean mapNullToDefault = method.getOptions()
            .getIterableMapping()
            .getNullValueMappingStrategy()
            .isReturnDefault();

        MethodReference factoryMethod = null;
        if ( !method.isUpdateMethod() ) {
            factoryMethod = ObjectFactoryMethodResolver.getFactoryMethod( method, null, ctx );
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
            selectionParameters,
            versionInformation
        );
    }

    private Assignment forge(SourceRHS sourceRHS, Type sourceType, Type targetType) {
        Assignment assignment = super.forgeMapping( sourceRHS, sourceType, targetType );
        if ( assignment != null ) {
            ctx.getMessager().note( 2, Message.ITERABLEMAPPING_CREATE_ELEMENT_NOTE, assignment );
        }
        return assignment;
    }

    protected abstract M instantiateMappingMethod(Method method, Collection<String> existingVariables,
                                                  Assignment assignment, MethodReference factoryMethod,
                                                  boolean mapNullToDefault, String loopVariableName,
                                                  List<LifecycleCallbackMethodReference> beforeMappingMethods,
                                                  List<LifecycleCallbackMethodReference> afterMappingMethods,
                                                  SelectionParameters selectionParameters,
                                                  VersionInformation versionInformation);

    protected abstract Type getElementType(Type parameterType);

    protected abstract Assignment getWrapper(Assignment assignment, Method method);

    @Override
    protected boolean shouldUsePropertyNamesInHistory() {
        return false;
    }
}
