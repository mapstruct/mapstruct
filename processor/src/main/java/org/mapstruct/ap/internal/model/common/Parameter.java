/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.VariableElement;

import org.mapstruct.ap.internal.prism.ContextPrism;
import org.mapstruct.ap.internal.prism.MappingTargetPrism;
import org.mapstruct.ap.internal.prism.TargetTypePrism;
import org.mapstruct.ap.internal.util.Collections;

/**
 * A parameter of a mapping method.
 *
 * @author Gunnar Morling
 */
public class Parameter extends ModelElement {

    private final String name;
    private final String originalName;
    private final Type type;
    private final boolean mappingTarget;
    private final boolean targetType;
    private final boolean mappingContext;

    private final boolean varArgs;

    private Parameter(String name, Type type, boolean mappingTarget, boolean targetType, boolean mappingContext,
                      boolean varArgs) {
        this.name = name;
        this.originalName = name;
        this.type = type;
        this.mappingTarget = mappingTarget;
        this.targetType = targetType;
        this.mappingContext = mappingContext;
        this.varArgs = varArgs;
    }

    public Parameter(String name, Type type) {
        this( name, type, false, false, false, false );
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public Type getType() {
        return type;
    }

    public boolean isMappingTarget() {
        return mappingTarget;
    }

    @Override
    public String toString() {
        return ( mappingTarget ? "@MappingTarget " : "" )
            + ( targetType ? "@TargetType " : "" )
            + ( mappingContext ? "@Context " : "" )
            + type.toString() + " " + name;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.asSet( type );
    }

    public boolean isTargetType() {
        return targetType;
    }

    public boolean isMappingContext() {
        return mappingContext;
    }

    public boolean isVarArgs() {
        return varArgs;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + ( type != null ? type.hashCode() : 0 );
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        Parameter parameter = (Parameter) o;

        if ( !Objects.equals( name, parameter.name ) ) {
            return false;
        }
        return Objects.equals( type, parameter.type );

    }

    public static Parameter forElementAndType(VariableElement element, Type parameterType, boolean isVarArgs) {
        return new Parameter(
            element.getSimpleName().toString(),
            parameterType,
            MappingTargetPrism.getInstanceOn( element ) != null,
            TargetTypePrism.getInstanceOn( element ) != null,
            ContextPrism.getInstanceOn( element ) != null,
            isVarArgs
        );
    }

    public static Parameter forForgedMappingTarget(Type parameterType) {
        return new Parameter(
            "mappingTarget",
            parameterType,
            true,
            false,
            false,
            false
        );
    }

    /**
     * @param parameters the parameters to filter
     * @return the parameters from the given list that are considered 'source parameters'
     */
    public static List<Parameter> getSourceParameters(List<Parameter> parameters) {
        return parameters.stream().filter( Parameter::isSourceParameter ).collect( Collectors.toList() );
    }

    /**
     * @param parameters the parameters to scan
     * @param sourceParameterName the source parameter name to match
     * @return the parameters from the given list that are considered 'source parameters'
     */
    public static Parameter getSourceParameter(List<Parameter> parameters, String sourceParameterName) {
        return parameters.stream()
                         .filter( Parameter::isSourceParameter )
                         .filter( parameter -> parameter.getName().equals( sourceParameterName ) )
                         .findAny()
                         .orElse( null );
    }

    /**
     * @param parameters the parameters to filter
     * @return the parameters from the given list that are marked as 'mapping context parameters'
     */
    public static List<Parameter> getContextParameters(List<Parameter> parameters) {
        return parameters.stream().filter( Parameter::isMappingContext ).collect( Collectors.toList() );
    }

    public static Parameter getMappingTargetParameter(List<Parameter> parameters) {
        return parameters.stream().filter( Parameter::isMappingTarget ).findAny().orElse( null );
    }

    public static Parameter getTargetTypeParameter(List<Parameter> parameters) {
        return parameters.stream().filter( Parameter::isTargetType ).findAny().orElse( null );
    }

    private static boolean isSourceParameter( Parameter parameter ) {
        return !parameter.isMappingTarget() && !parameter.isTargetType() && !parameter.isMappingContext();
    }

}
