/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import org.mapstruct.ap.model.common.Accessibility;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.util.Strings;

/**
 * Represents a mapping method with source and target type and the mappings between the properties of source and target
 * type.
 * <p>
 * A method can either be configured by itself or by another method for the inverse mapping direction (one of
 * {@link #setMappings(Map)}, {@link #setIterableMapping(IterableMapping)} or {@link #setMapMapping(MapMapping)} will be
 * called in this case).
 *
 * @author Gunnar Morling
 */
public class SourceMethod implements Method {

    private final Type declaringMapper;
    private final ExecutableElement executable;
    private final List<Parameter> parameters;
    private final Parameter targetParameter;
    private final Type returnType;
    private final Accessibility accessibility;
    private final Types typeUtils;
    private final List<Type> exceptionTypes;

    private Map<String, List<Mapping>> mappings;
    private IterableMapping iterableMapping;
    private MapMapping mapMapping;

    private boolean configuredByReverseMappingMethod = false;

    public static SourceMethod forMethodRequiringImplementation(ExecutableElement executable,
                                                                List<Parameter> parameters,
                                                                Type returnType,
                                                                List<Type> exceptionTypes,
                                                                Map<String, List<Mapping>> mappings,
                                                                IterableMapping iterableMapping, MapMapping mapMapping,
                                                                Types typeUtils) {

        return new SourceMethod(
            null,
            executable,
            parameters,
            returnType,
            exceptionTypes,
            mappings,
            iterableMapping,
            mapMapping,
            typeUtils );
    }

    public static SourceMethod forReferencedMethod(Type declaringMapper, ExecutableElement executable,
                                                   List<Parameter> parameters, Type returnType,
                                                   List<Type> exceptionTypes, Types typeUtils) {

        return new SourceMethod(
            declaringMapper,
            executable,
            parameters,
            returnType,
            exceptionTypes,
            Collections.<String, List<Mapping>> emptyMap(),
            null,
            null,
            typeUtils );
    }

    public static SourceMethod forFactoryMethod(Type declaringMapper, ExecutableElement executable, Type returnType,
                                                List<Type> exceptionTypes, Types typeUtils) {

        return new SourceMethod(
            declaringMapper,
            executable,
            Collections.<Parameter> emptyList(),
            returnType,
            exceptionTypes,
            Collections.<String, List<Mapping>> emptyMap(),
            null,
            null,
            typeUtils );
    }

    private SourceMethod(Type declaringMapper, ExecutableElement executable, List<Parameter> parameters,
                         Type returnType, List<Type> exceptionTypes, Map<String, List<Mapping>> mappings,
                         IterableMapping iterableMapping, MapMapping mapMapping, Types typeUtils) {
        this.declaringMapper = declaringMapper;
        this.executable = executable;
        this.parameters = parameters;
        this.returnType = returnType;
        this.exceptionTypes = exceptionTypes;
        this.mappings = mappings;
        this.iterableMapping = iterableMapping;
        this.mapMapping = mapMapping;
        this.accessibility = Accessibility.fromModifiers( executable.getModifiers() );

        this.targetParameter = determineTargetParameter( parameters );

        this.typeUtils = typeUtils;
    }

    private Parameter determineTargetParameter(Iterable<Parameter> parameters) {
        for ( Parameter parameter : parameters ) {
            if ( parameter.isMappingTarget() ) {
                return parameter;
            }
        }

        return null;
    }

    /**
     * {@inheritDoc} {@link Method}
     */
    @Override
    public Type getDeclaringMapper() {
        return declaringMapper;
    }

    public ExecutableElement getExecutable() {
        return executable;
    }

    /**
     * {@inheritDoc} {@link Method}
     */
    @Override
    public String getName() {
        return executable.getSimpleName().toString();
    }

    /**
     * {@inheritDoc} {@link Method}
     */
    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * {@inheritDoc} {@link Method}
     */
    @Override
    public List<Parameter> getSourceParameters() {
        List<Parameter> sourceParameters = new ArrayList<Parameter>();

        for ( Parameter parameter : parameters ) {
            if ( !parameter.isMappingTarget() && !parameter.isTargetType() ) {
                sourceParameters.add( parameter );
            }
        }

        return sourceParameters;
    }

    @Override
    public List<String> getParameterNames() {
        List<String> parameterNames = new ArrayList<String>( parameters.size() );

        for ( Parameter parameter : parameters ) {
            parameterNames.add( parameter.getName() );
        }

        return parameterNames;
    }

    @Override
    public Type getResultType() {
        return targetParameter != null ? targetParameter.getType() : returnType;
    }

    /**
     * {@inheritDoc} {@link Method}
     */
    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public Accessibility getAccessibility() {
        return accessibility;
    }

    /**
     * @return the {@link Mapping}s configured for this method, keyed by source property name.
     */
    public Map<String, List<Mapping>> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, List<Mapping>> mappings) {
        this.mappings = mappings;
        this.configuredByReverseMappingMethod = true;
    }

    public IterableMapping getIterableMapping() {
        return iterableMapping;
    }

    public void setIterableMapping(IterableMapping iterableMapping) {
        this.iterableMapping = iterableMapping;
        this.configuredByReverseMappingMethod = true;
    }

    public MapMapping getMapMapping() {
        return mapMapping;
    }

    public void setMapMapping(MapMapping mapMapping) {
        this.mapMapping = mapMapping;
        this.configuredByReverseMappingMethod = true;
    }

    public boolean reverses(SourceMethod method) {
        return getSourceParameters().size() == 1 && method.getSourceParameters().size() == 1
            && equals( getSourceParameters().iterator().next().getType(), method.getResultType() )
            && equals( getResultType(), method.getSourceParameters().iterator().next().getType() );
    }

    /**
     * {@inheritDoc} {@link Method}
     */
    @Override
    public Parameter getTargetParameter() {
        return targetParameter;
    }

    public boolean isIterableMapping() {
        return getSourceParameters().size() == 1 && getSourceParameters().iterator().next().getType().isIterableType()
            && getResultType().isIterableType();
    }

    public boolean isMapMapping() {
        return getSourceParameters().size() == 1 && getSourceParameters().iterator().next().getType().isMapType()
            && getResultType().isMapType();
    }

    public boolean isEnumMapping() {
        return getSourceParameters().size() == 1 && getSourceParameters().iterator().next().getType().isEnumType()
            && getResultType().isEnumType();
    }

    /**
     * Whether this method is configured by itself or by the corresponding reverse mapping method.
     *
     * @return {@code true} if this method is configured by itself, {@code false} otherwise.
     */
    public boolean isConfiguredByReverseMappingMethod() {
        return configuredByReverseMappingMethod;
    }

    private boolean equals(Object o1, Object o2) {
        return ( o1 == null && o2 == null ) || ( o1 != null ) && o1.equals( o2 );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( returnType.toString() );
        sb.append( " " );

        if ( declaringMapper != null ) {
            sb.append( declaringMapper ).append( "." );
        }

        sb.append( getName() ).append( "(" ).append( Strings.join( parameters, ", " ) ).append( ")" );

        return sb.toString();
    }

    /**
     * Returns the {@link Mapping} for the given target property. May return {@code null}.
     */
    public Mapping getMappingByTargetPropertyName(String targetPropertyName) {
        for ( Map.Entry<String, List<Mapping>> entry : mappings.entrySet() ) {
            for ( Mapping mapping : entry.getValue() ) {
                if ( mapping.getTargetName().equals( targetPropertyName ) ) {
                    return mapping;
                }
            }
        }
        return null;
    }

    public Parameter getSourceParameter(String sourceParameterName) {
        for ( Parameter parameter : getSourceParameters() ) {
            if ( parameter.getName().equals( sourceParameterName ) ) {
                return parameter;
            }
        }

        return null;
    }

    /**
     * Whether an implementation of this method must be generated or not.
     *
     * @return true when an implementation is required
     */
    @Override
    public boolean overridesMethod() {
        return declaringMapper == null && executable.getModifiers().contains( Modifier.ABSTRACT );
    }

    /**
     * {@inheritDoc} {@link Method}
     */
    @Override
    public boolean matches(List<Type> sourceTypes, Type targetType) {
        MethodMatcher matcher = new MethodMatcher( typeUtils, this );
        return matcher.matches( sourceTypes, targetType );
    }

    /**
     * @param parameters the parameter list to check
     * @return <code>true</code>, iff the parameter list contains a parameter annotated with {@code @TargetType}
     */
    public static boolean containsTargetTypeParameter(List<Parameter> parameters) {
        for ( Parameter param : parameters ) {
            if ( param.isTargetType() ) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Type> getThrownTypes() {
        return exceptionTypes;
    }

    @Override
    public void printMessage( Messager messager, Kind kind, String message ) {
        messager.printMessage( kind, message, executable );
    }

}
