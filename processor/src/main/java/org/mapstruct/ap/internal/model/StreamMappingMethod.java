/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.type.TypeKind;

import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.assignment.Java8FunctionWrapper;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.ForgedMethodHistory;
import org.mapstruct.ap.internal.model.source.FormattingParameters;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.util.JavaStreamConstants;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one iterable or array type to Stream.
 * The collection elements are mapped either by a {@link TypeConversion} or another mapping method.
 *
 * @author Filip Hrisafov
 */
public class StreamMappingMethod extends MappingMethod {

    private final Assignment elementAssignment;
    private final MethodReference factoryMethod;
    private final boolean overridden;
    private final boolean mapNullToDefault;
    private final String loopVariableName;
    private final SelectionParameters selectionParameters;
    private final Set<Type> helperImports;

    public static class Builder {

        private Method method;
        private MappingBuilderContext ctx;
        private SelectionParameters selectionParameters;
        private FormattingParameters formattingParameters;
        private NullValueMappingStrategyPrism nullValueMappingStrategy;
        private ForgedMethod forgedMethod;

        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public Builder method(Method sourceMethod) {
            this.method = sourceMethod;
            return this;
        }

        public Builder formattingParameters(FormattingParameters formattingParameters) {
            this.formattingParameters = formattingParameters;
            return this;
        }

        public Builder selectionParameters(SelectionParameters selectionParameters) {
            this.selectionParameters = selectionParameters;
            return this;
        }

        public Builder nullValueMappingStrategy(NullValueMappingStrategyPrism nullValueMappingStrategy) {
            this.nullValueMappingStrategy = nullValueMappingStrategy;
            return this;
        }

        public StreamMappingMethod build() {
            //TODO the building of the methods is the same as the IterableMappingMethod, the only difference being
            // the static extracted getElementType and the wrapper of the assignment using Java8FunctionWrapper

            Type sourceParameterType = first( method.getSourceParameters() ).getType();
            Type resultType = method.getResultType();

            Type sourceElementType = getElementType( sourceParameterType );
            Type targetElementType = getElementType( resultType );

            String loopVariableName =
                Strings.getSaveVariableName( sourceElementType.getName(), method.getParameterNames() );

            SourceRHS sourceRHS = new SourceRHS( loopVariableName, sourceElementType, new HashSet<String>(),
                "stream element"
            );
            Assignment assignment = ctx.getMappingResolver().getTargetAssignment(
                method,
                targetElementType,
                null, // there is no targetPropertyName
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

            assignment = new Java8FunctionWrapper(
                assignment,
                new ArrayList<Type>(),
                ctx.getTypeFactory().getType( JavaStreamConstants.FUNCTION_FQN )
            );

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

            Set<Type> helperImports = new HashSet<Type>();
            if ( resultType.isIterableType() ) {
                helperImports.add( ctx.getTypeFactory().getType( JavaStreamConstants.COLLECTORS_FQN ) );
            }

            if ( !sourceParameterType.isCollectionType() && !sourceParameterType.isArrayType() &&
                sourceParameterType.isIterableType() ) {
                helperImports.add( ctx.getTypeFactory().getType( JavaStreamConstants.STREAM_SUPPORT_FQN ) );
            }

            return new StreamMappingMethod(
                method,
                assignment,
                factoryMethod,
                mapNullToDefault,
                loopVariableName,
                beforeMappingMethods,
                afterMappingMethods,
                selectionParameters,
                helperImports,
                forgedMethod
            );
        }

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

    private StreamMappingMethod(Method method, Assignment parameterAssignment, MethodReference factoryMethod,
                                boolean mapNullToDefault, String loopVariableName,
                                List<LifecycleCallbackMethodReference> beforeMappingReferences,
                                List<LifecycleCallbackMethodReference> afterMappingReferences,
                                SelectionParameters selectionParameters, Set<Type> helperImports,
                                ForgedMethod forgedMethod) {
        super( method, beforeMappingReferences, afterMappingReferences,
            forgedMethod == null ? new ArrayList<ForgedMethod>() : Collections.singletonList( forgedMethod )
        );
        this.elementAssignment = parameterAssignment;
        this.factoryMethod = factoryMethod;
        this.overridden = method.overridesMethod();
        this.mapNullToDefault = mapNullToDefault;
        this.loopVariableName = loopVariableName;
        this.selectionParameters = selectionParameters;
        this.helperImports = helperImports;
    }

    public Parameter getSourceParameter() {
        for ( Parameter parameter : getParameters() ) {
            if ( !parameter.isMappingTarget() ) {
                return parameter;
            }
        }

        throw new IllegalStateException( "Method " + this + " has no source parameter." );
    }

    public Assignment getElementAssignment() {
        return elementAssignment;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();
        if ( elementAssignment != null ) {
            types.addAll( elementAssignment.getImportTypes() );
        }
        if ( ( factoryMethod == null ) && ( !isExistingInstanceMapping() ) ) {
            if ( getReturnType().getImplementationType() != null ) {
                types.addAll( getReturnType().getImplementationType().getImportTypes() );
            }
        }

        types.addAll( helperImports );

        return types;
    }

    public boolean isMapNullToDefault() {
        return mapNullToDefault;
    }

    public boolean isOverridden() {
        return overridden;
    }

    public String getLoopVariableName() {
        return loopVariableName;
    }

    public String getDefaultValue() {
        TypeKind kind = getResultElementType().getTypeMirror().getKind();
        switch ( kind ) {
            case BOOLEAN:
                return "false";
            case BYTE:
            case SHORT:
            case INT:
            case CHAR:  /*"'\u0000'" would have been better, but depends on platformencoding */
                return "0";
            case LONG:
                return "0L";
            case FLOAT:
                return "0.0f";
            case DOUBLE:
                return "0.0d";
            default:
                return "null";
        }
    }

    public MethodReference getFactoryMethod() {
        return this.factoryMethod;
    }

    public Type getSourceElementType() {
        return getElementType( getSourceParameter().getType() );
    }

    public Type getResultElementType() {
        return getElementType( getResultType() );
    }

    public String getIndex1Name() {
        return Strings.getSaveVariableName( "i", loopVariableName, getSourceParameter().getName(), getResultName() );
    }

    public String getIndex2Name() {
        return Strings.getSaveVariableName( "j", loopVariableName, getSourceParameter().getName(), getResultName() );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( getResultType() == null ) ? 0 : getResultType().hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        StreamMappingMethod other = (StreamMappingMethod) obj;

        if ( !getResultType().equals( other.getResultType() ) ) {
            return false;
        }

        if ( getSourceParameters().size() != other.getSourceParameters().size() ) {
            return false;
        }

        for ( int i = 0; i < getSourceParameters().size(); i++ ) {
            if ( !getSourceParameters().get( i ).getType().equals( other.getSourceParameters().get( i ).getType() ) ) {
                return false;
            }

            List<Type> thisTypeParameters = getSourceParameters().get( i ).getType().getTypeParameters();
            List<Type> otherTypeParameters = other.getSourceParameters().get( i ).getType().getTypeParameters();

            if ( !thisTypeParameters.equals( otherTypeParameters ) ) {
                return false;
            }
        }

        if ( this.selectionParameters != null ) {
            if ( !this.selectionParameters.equals( other.selectionParameters ) ) {
                return false;
            }
        }
        else if ( other.selectionParameters != null ) {
            return false;
        }

        return isMapNullToDefault() == other.isMapNullToDefault();
    }

    private static Type getElementType(Type parameterType) {
        if ( parameterType.isArrayType() ) {
            return parameterType.getComponentType();
        }
        else if ( parameterType.isIterableType() ) {
            return first( parameterType.determineTypeArguments( Iterable.class ) ).getTypeBound();
        }
        else if ( parameterType.isStreamType() ) {
            return first( parameterType.determineTypeArguments( JavaStreamConstants.STREAM_FQN ) ).getTypeBound();
        }

        throw new IllegalArgumentException( "Could not get the element type" );

    }

}
