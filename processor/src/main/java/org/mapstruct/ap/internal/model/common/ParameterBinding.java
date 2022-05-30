/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
    private final String targetPropertyName;
    private final SourceRHS sourceRHS;

    private ParameterBinding(Type parameterType, String variableName, boolean mappingTarget, boolean targetType,
        boolean mappingContext, String targetPropertyName, SourceRHS sourceRHS) {
        this.type = parameterType;
        this.variableName = variableName;
        this.targetType = targetType;
        this.mappingTarget = mappingTarget;
        this.mappingContext = mappingContext;
        this.targetPropertyName = targetPropertyName;
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
     * @return {@code true}, if the parameter being bound is a {@code @TargetPropertyName} parameter.
     */
    public boolean isTargetPropertyName() {
      return targetPropertyName != null && !targetPropertyName.isEmpty();
    }

    /**
     * @return the name of the target property in context
     * if the parameter being bound is a {@code @TargetPropertyName} parameter.
     */
    public String getTargetPropertyNameValue() {
      return targetPropertyName;
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
            null,
            null
        );
    }

    /**
     * @param parameter parameter
     * @return a parameter binding reflecting the given parameter as being used as argument for a method call
     */
    public static ParameterBinding fromTargetPropertyNameParameter(Parameter parameter, String targetProperty) {
      return new ParameterBinding(
        parameter.getType(),
        parameter.getName(),
        parameter.isMappingTarget(),
        parameter.isTargetType(),
        parameter.isMappingContext(),
        targetProperty,
        null
      );
    }

    public static List<ParameterBinding> fromParameters(List<Parameter> parameters) {
        List<ParameterBinding> result = new ArrayList<>( parameters.size() );
        for ( Parameter param : parameters ) {
            result.add( fromParameter( param ) );
        }
        return result;
    }

    public static ParameterBinding fromTypeAndName(Type parameterType, String parameterName) {
        return new ParameterBinding(
            parameterType,
            parameterName,
            false,
            false,
            false,
            null,
            null
        );
    }

    /**
     * @param classTypeOf the type representing {@code Class<X>} for the target type {@code X}
     * @return a parameter binding representing a target type parameter
     */
    public static ParameterBinding forTargetTypeBinding(Type classTypeOf) {
        return new ParameterBinding( classTypeOf, null, false, true, false, null, null );
    }

    /**
     * @param resultType type of the mapping target
     * @return a parameter binding representing a mapping target parameter
     */
    public static ParameterBinding forMappingTargetBinding(Type resultType) {
        return new ParameterBinding( resultType, null, true, false, false, null, null );
    }

    /**
     * @param sourceType type of the parameter
     * @return a parameter binding representing a mapping source type
     */
    public static ParameterBinding forSourceTypeBinding(Type sourceType) {
        return new ParameterBinding( sourceType, null, false, false, false, null, null );
    }

    public static ParameterBinding fromSourceRHS(SourceRHS sourceRHS) {
        return new ParameterBinding( sourceRHS.getSourceType(), null, false, false, false, null, sourceRHS );
    }
}
