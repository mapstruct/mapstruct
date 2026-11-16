/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
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
    private final SourceRHS sourceRHS;
    private final Collection<BindingType> bindingTypes;

    private ParameterBinding(Type parameterType, String variableName, Collection<BindingType> bindingTypes,
                             SourceRHS sourceRHS) {
        this.type = parameterType;
        this.variableName = variableName;
        this.bindingTypes = bindingTypes;
        this.sourceRHS = sourceRHS;
    }

    /**
     * @return the name of the variable (or parameter) that is being used as argument for the parameter being bound.
     */
    public String getVariableName() {
        return variableName;
    }

    public boolean isSourceParameter() {
        return bindingTypes.contains( BindingType.PARAMETER );
    }

    /**
     * @return {@code true}, if the parameter being bound is a {@code @TargetType} parameter.
     */
    public boolean isTargetType() {
        return bindingTypes.contains( BindingType.TARGET_TYPE );
    }

    /**
     * @return {@code true}, if the parameter being bound is a {@code @MappingTarget} parameter.
     */
    public boolean isMappingTarget() {
        return bindingTypes.contains( BindingType.MAPPING_TARGET );
    }

    /**
     * @return {@code true}, if the parameter being bound is a {@code @MappingContext} parameter.
     */
    public boolean isMappingContext() {
        return bindingTypes.contains( BindingType.CONTEXT );
    }

    public boolean isForSourceRhs() {
        return bindingTypes.contains( BindingType.SOURCE_RHS );
    }

    /**
     * @return {@code true}, if the parameter being bound is a {@code @SourcePropertyName} parameter.
     */
    public boolean isSourcePropertyName() {
        return bindingTypes.contains( BindingType.SOURCE_PROPERTY_NAME );
    }

    /**
     * @return {@code true}, if the parameter being bound is a {@code @TargetPropertyName} parameter.
     */
    public boolean isTargetPropertyName() {
        return bindingTypes.contains( BindingType.TARGET_PROPERTY_NAME );
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
        if ( isTargetType() ) {
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
        EnumSet<BindingType> bindingTypes = EnumSet.of( BindingType.PARAMETER );
        if ( parameter.isMappingTarget() ) {
            bindingTypes.add( BindingType.MAPPING_TARGET );
        }

        if ( parameter.isTargetType() ) {
            bindingTypes.add( BindingType.TARGET_TYPE );
        }

        if ( parameter.isMappingContext() ) {
            bindingTypes.add( BindingType.CONTEXT );
        }

        if ( parameter.isSourcePropertyName() ) {
            bindingTypes.add( BindingType.SOURCE_PROPERTY_NAME );
        }

        if ( parameter.isTargetPropertyName() ) {
            bindingTypes.add( BindingType.TARGET_PROPERTY_NAME );
        }

        return new ParameterBinding(
            parameter.getType(),
            parameter.getName(),
            bindingTypes,
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
            Collections.emptySet(),
            null
        );
    }

    /**
     * @param classTypeOf the type representing {@code Class<X>} for the target type {@code X}
     * @return a parameter binding representing a target type parameter
     */
    public static ParameterBinding forTargetTypeBinding(Type classTypeOf) {
        return new ParameterBinding( classTypeOf, null, Collections.singleton( BindingType.TARGET_TYPE ), null );
    }

    /**
     * @return a parameter binding representing a target property name parameter
     */
    public static ParameterBinding forTargetPropertyNameBinding(Type classTypeOf) {
        return new ParameterBinding(
            classTypeOf,
            null,
            Collections.singleton( BindingType.TARGET_PROPERTY_NAME ),
            null
        );
    }

    /**
     * @return a parameter binding representing a source property name parameter
     */
    public static ParameterBinding forSourcePropertyNameBinding(Type classTypeOf) {
        return new ParameterBinding(
            classTypeOf,
            null,
            Collections.singleton( BindingType.SOURCE_PROPERTY_NAME ),
            null
        );
    }

    /**
     * @param resultType type of the mapping target
     * @return a parameter binding representing a mapping target parameter
     */
    public static ParameterBinding forMappingTargetBinding(Type resultType) {
        return new ParameterBinding( resultType, null, Collections.singleton( BindingType.MAPPING_TARGET ), null );
    }

    /**
     * @param sourceType type of the parameter
     * @return a parameter binding representing a mapping source type
     */
    public static ParameterBinding forSourceTypeBinding(Type sourceType) {
        return new ParameterBinding( sourceType, null, Collections.singleton( BindingType.SOURCE_TYPE ), null );
    }

    public static ParameterBinding fromSourceRHS(SourceRHS sourceRHS) {
        return new ParameterBinding(
            sourceRHS.getSourceType(),
            null,
            Collections.singleton( BindingType.SOURCE_RHS ),
            sourceRHS
        );
    }

    enum BindingType {
        PARAMETER,
        FROM_TYPE_AND_NAME,
        TARGET_TYPE,
        TARGET_PROPERTY_NAME,
        SOURCE_PROPERTY_NAME,
        MAPPING_TARGET,
        CONTEXT,
        SOURCE_TYPE,
        SOURCE_RHS
    }
}
