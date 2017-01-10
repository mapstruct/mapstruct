/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.internal.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.ForgedMethodHistory;
import org.mapstruct.ap.internal.model.source.FormattingParameters;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.util.Collections.first;

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

    private Method method;
    private SelectionParameters selectionParameters;
    private FormattingParameters formattingParameters;
    private NullValueMappingStrategyPrism nullValueMappingStrategy;
    private ForgedMethod forgedMethod;
    private String errorMessagePart;
    private String callingContextTargetPropertyName;

    ContainerMappingMethodBuilder(Class<B> selfType, String errorMessagePart) {
        super( selfType );
        this.errorMessagePart = errorMessagePart;
    }

    public B method(Method sourceMethod) {
        this.method = sourceMethod;
        return myself;
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

    public final M build() {
        Type sourceParameterType = first( method.getSourceParameters() ).getType();
        Type resultType = method.getResultType();

        Type sourceElementType = getElementType( sourceParameterType );
        Type targetElementType = getElementType( resultType );

        String loopVariableName =
            Strings.getSaveVariableName( sourceElementType.getName(), method.getParameterNames() );

        SourceRHS sourceRHS = new SourceRHS(
            loopVariableName,
            sourceElementType,
            new HashSet<String>(),
            errorMessagePart
        );
        Assignment assignment = ctx.getMappingResolver().getTargetAssignment(
            method,
            targetElementType,
            callingContextTargetPropertyName,
            formattingParameters,
            selectionParameters,
            sourceRHS,
            false
        );

        if ( assignment == null ) {
            assignment = forgeMapping( sourceRHS, sourceElementType, targetElementType );
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
            factoryMethod = ctx.getMappingResolver().getFactoryMethod( method, method.getResultType(), null );
        }

        Set<String> existingVariables = new HashSet<String>( method.getParameterNames() );
        existingVariables.add( loopVariableName );

        List<LifecycleCallbackMethodReference> beforeMappingMethods = LifecycleCallbackFactory.beforeMappingMethods(
            method,
            selectionParameters,
            ctx,
            existingVariables
        );
        List<LifecycleCallbackMethodReference> afterMappingMethods = LifecycleCallbackFactory.afterMappingMethods(
            method,
            selectionParameters,
            ctx,
            existingVariables
        );

        return instantiateMappingMethod(
            method,
            assignment,
            factoryMethod,
            mapNullToDefault,
            loopVariableName,
            beforeMappingMethods,
            afterMappingMethods,
            selectionParameters,
            forgedMethod
        );
    }

    protected abstract M instantiateMappingMethod(Method method, Assignment assignment, MethodReference factoryMethod,
                                                  boolean mapNullToDefault, String loopVariableName,
                                                  List<LifecycleCallbackMethodReference> beforeMappingMethods,
                                                  List<LifecycleCallbackMethodReference> afterMappingMethods,
                                                  SelectionParameters selectionParameters, ForgedMethod forgedMethod);

    protected abstract Type getElementType(Type parameterType);

    protected abstract Assignment getWrapper(Assignment assignment, Method method);

    private Assignment forgeMapping(SourceRHS sourceRHS, Type sourceType, Type targetType) {
        ForgedMethodHistory forgedMethodHistory = null;
        if ( method instanceof ForgedMethod ) {
            forgedMethodHistory = ( (ForgedMethod) method ).getHistory();
        }
        String name = getName( sourceType, targetType );
        forgedMethod = new ForgedMethod(
            name,
            sourceType,
            targetType,
            method.getMapperConfiguration(),
            method.getExecutable(),
            method.getContextParameters(),
            forgedMethodHistory
        );

        Assignment assignment = new MethodReference(
            forgedMethod,
            null,
            ParameterBinding.fromParameters( forgedMethod.getParameters() )
        );

        assignment.setAssignment( sourceRHS );

        return assignment;
    }

    private String getName(Type sourceType, Type targetType) {
        String fromName = getName( sourceType );
        String toName = getName( targetType );
        return Strings.decapitalize( fromName + "To" + toName );
    }

    private String getName(Type type) {
        StringBuilder builder = new StringBuilder();
        for ( Type typeParam : type.getTypeParameters() ) {
            builder.append( typeParam.getIdentification() );
        }
        builder.append( type.getIdentification() );
        return builder.toString();
    }
}
