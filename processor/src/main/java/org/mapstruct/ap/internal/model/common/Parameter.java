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
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;

import org.mapstruct.ap.internal.gem.ContextGem;
import org.mapstruct.ap.internal.gem.MappingTargetGem;
import org.mapstruct.ap.internal.gem.SourcePropertyNameGem;
import org.mapstruct.ap.internal.gem.TargetPropertyNameGem;
import org.mapstruct.ap.internal.gem.TargetTypeGem;
import org.mapstruct.ap.internal.util.Collections;

/**
 * A parameter of a mapping method.
 *
 * @author Gunnar Morling
 */
public class Parameter extends ModelElement {

    private final Element element;
    private final String name;
    private final String originalName;
    private final Type type;
    private final boolean mappingTarget;
    private final boolean targetType;
    private final boolean mappingContext;
    private final boolean sourcePropertyName;
    private final boolean targetPropertyName;

    private final boolean varArgs;

    private Parameter(Element element, Type type, boolean varArgs) {
        this.element = element;
        this.name = element.getSimpleName().toString();
        this.originalName = name;
        this.type = type;
        this.mappingTarget = MappingTargetGem.instanceOn( element ) != null;
        this.targetType = TargetTypeGem.instanceOn( element ) != null;
        this.mappingContext = ContextGem.instanceOn( element ) != null;
        this.sourcePropertyName = SourcePropertyNameGem.instanceOn( element ) != null;
        this.targetPropertyName = TargetPropertyNameGem.instanceOn( element ) != null;
        this.varArgs = varArgs;
    }

    private Parameter(String name, Type type, boolean mappingTarget, boolean targetType, boolean mappingContext,
                      boolean sourcePropertyName, boolean targetPropertyName,
                      boolean varArgs) {
        this.element = null;
        this.name = name;
        this.originalName = name;
        this.type = type;
        this.mappingTarget = mappingTarget;
        this.targetType = targetType;
        this.mappingContext = mappingContext;
        this.sourcePropertyName = sourcePropertyName;
        this.targetPropertyName = targetPropertyName;
        this.varArgs = varArgs;
    }

    public Parameter(String name, Type type) {
        this( name, type, false, false, false, false, false, false );
    }

    public Element getElement() {
        return element;
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
        return String.format( format(), type );
    }

    public String describe() {
        return String.format( format(), type.describe() );
    }

    private String format() {
        return ( mappingTarget ? "@MappingTarget " : "" )
            + ( targetType ? "@TargetType " : "" )
            + ( mappingContext ? "@Context " : "" )
            + ( sourcePropertyName ? "@SourcePropertyName " : "" )
            + ( targetPropertyName ? "@TargetPropertyName " : "" )
            +  "%s " + name;
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

    public boolean isTargetPropertyName() {
        return targetPropertyName;
    }

    public boolean isSourcePropertyName() {
        return sourcePropertyName;
    }

    public boolean isVarArgs() {
        return varArgs;
    }

    public boolean isSourceParameter() {
        return !isMappingTarget() &&
            !isTargetType() &&
            !isMappingContext() &&
            !isSourcePropertyName() &&
            !isTargetPropertyName();
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
            element,
            parameterType,
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

    public static Parameter getSourcePropertyNameParameter(List<Parameter> parameters) {
        return parameters.stream().filter( Parameter::isSourcePropertyName ).findAny().orElse( null );
    }

    public static Parameter getTargetPropertyNameParameter(List<Parameter> parameters) {
      return parameters.stream().filter( Parameter::isTargetPropertyName ).findAny().orElse( null );
    }

}
