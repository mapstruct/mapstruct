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
package org.mapstruct.ap.internal.model.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Represents how one parameter of a method to be called is populated.
 *
 * @author Andreas Gudian
 */
public class ParameterBinding {

    private final Type type;
    private final String variableName;
    private final boolean targetType;
    private final boolean mappingTarget;
    private final boolean mappingContext;
    private final SourceRHS sourceRHS;

    private ParameterBinding(Type parameterType, String variableName, boolean mappingTarget, boolean targetType,
        boolean mappingContext, SourceRHS sourceRHS) {
        this.type = parameterType;
        this.variableName = variableName;
        this.targetType = targetType;
        this.mappingTarget = mappingTarget;
        this.mappingContext = mappingContext;
        this.sourceRHS = sourceRHS;
    }

    /**
     * @return the name of the variable (or parameter) that is being used as argument for the parameter being bound.
     */
    public String getVariableName() {
        return variableName;
    }

    /**
     * @return {@code true}, if the parameter being bound is a {@code @TargetType} parameter.
     */
    public boolean isTargetType() {
        return targetType;
    }

    /**
     * @return {@code true}, if the parameter being bound is a {@code @MappingTarget} parameter.
     */
    public boolean isMappingTarget() {
        return mappingTarget;
    }

    /**
     * @return {@code true}, if the parameter being bound is a {@code @MappingContext} parameter.
     */
    public boolean isMappingContext() {
        return mappingContext;
    }

    /**
     * @return the type of the parameter that is bound
     */
    public Type getType() {
        return type;
    }

    /**
     * @return the sourceRHS that this parameter is bound to
     */
    public SourceRHS getSourceRHS() {
        return sourceRHS;
    }

    public Set<Type> getImportTypes() {
        if ( targetType ) {
            return type.getImportTypes();
        }

        if ( sourceRHS != null ) {
            return sourceRHS.getImportTypes();
        }

        return Collections.emptySet();
    }

    /**
     * @param parameter parameter
     * @return a parameter binding reflecting the given parameter as being used as argument for a method call
     */
    public static ParameterBinding fromParameter(Parameter parameter) {
        return new ParameterBinding(
            parameter.getType(),
            parameter.getName(),
            parameter.isMappingTarget(),
            parameter.isTargetType(),
            parameter.isMappingContext(),
            null
        );
    }

    public static List<ParameterBinding> fromParameters(List<Parameter> parameters) {
        List<ParameterBinding> result = new ArrayList<ParameterBinding>( parameters.size() );
        for ( Parameter param : parameters ) {
            result.add( fromParameter( param ) );
        }
        return result;
    }

    /**
     * @param classTypeOf the type representing {@code Class<X>} for the target type {@code X}
     * @return a parameter binding representing a target type parameter
     */
    public static ParameterBinding forTargetTypeBinding(Type classTypeOf) {
        return new ParameterBinding( classTypeOf, null, false, true, false, null );
    }

    /**
     * @param resultType type of the mapping target
     * @return a parameter binding representing a mapping target parameter
     */
    public static ParameterBinding forMappingTargetBinding(Type resultType) {
        return new ParameterBinding( resultType, null, true, false, false, null );
    }

    /**
     * @param sourceType type of the parameter
     * @return a parameter binding representing a mapping source type
     */
    public static ParameterBinding forSourceTypeBinding(Type sourceType) {
        return new ParameterBinding( sourceType, null, false, false, false, null );
    }

    public static ParameterBinding fromSourceRHS(SourceRHS sourceRHS) {
        return new ParameterBinding( sourceRHS.getSourceType(), null, false, false, false, sourceRHS );
    }
}
