/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Types;

import org.mapstruct.ap.model.common.Accessibility;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.model.source.SourceReference.PropertyEntry;
import org.mapstruct.ap.util.MapperConfig;
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

    private final Types typeUtils;
    private final TypeFactory typeFactory;
    private final Messager messager;

    private final Type declaringMapper;
    private final ExecutableElement executable;
    private final List<Parameter> parameters;
    private final Parameter targetParameter;
    private final Type returnType;
    private final Accessibility accessibility;
    private final List<Type> exceptionTypes;
    private final MapperConfig config;

    private Map<String, List<Mapping>> mappings;
    private IterableMapping iterableMapping;
    private MapMapping mapMapping;

    public static class Builder {

        private Type declaringMapper = null;
        private ExecutableElement executable;
        private List<Parameter> parameters;
        private Type returnType = null;
        private List<Type> exceptionTypes;
        private Map<String, List<Mapping>> mappings;
        private IterableMapping iterableMapping = null;
        private MapMapping mapMapping = null;
        private Types typeUtils;
        private TypeFactory typeFactory = null;
        private Messager messager = null;
        private MapperConfig mapperConfig = null;

        public Builder() {
        }

        public Builder setDeclaringMapper(Type declaringMapper) {
            this.declaringMapper = declaringMapper;
            return this;
        }

        public Builder setExecutable(ExecutableElement executable) {
            this.executable = executable;
            return this;
        }

        public Builder setParameters(List<Parameter> parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder setReturnType(Type returnType) {
            this.returnType = returnType;
            return this;
        }

        public Builder setExceptionTypes(List<Type> exceptionTypes) {
            this.exceptionTypes = exceptionTypes;
            return this;
        }

        public Builder setMappings(Map<String, List<Mapping>> mappings) {
            this.mappings = mappings;
            return this;
        }

        public Builder setIterableMapping(IterableMapping iterableMapping) {
            this.iterableMapping = iterableMapping;
            return this;
        }

        public Builder setMapMapping(MapMapping mapMapping) {
            this.mapMapping = mapMapping;
            return this;
        }

        public Builder setTypeUtils(Types typeUtils) {
            this.typeUtils = typeUtils;
            return this;
        }

        public Builder setTypeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public Builder setMessager(Messager messager) {
            this.messager = messager;
            return this;
        }

        public Builder setMapperConfig(MapperConfig mapperConfig) {
            this.mapperConfig = mapperConfig;
            return this;
        }

        public SourceMethod createSourceMethod() {
            SourceMethod sourceMethod = new SourceMethod(
                    declaringMapper,
                    executable,
                    parameters,
                    returnType,
                    exceptionTypes,
                    mappings,
                    iterableMapping,
                    mapMapping,
                    typeUtils,
                    typeFactory,
                    messager,
                    mapperConfig
            );

            if ( mappings != null ) {
                for ( Map.Entry<String, List<Mapping>> entry : sourceMethod.getMappings().entrySet() ) {
                    for ( Mapping mapping : entry.getValue() ) {
                        mapping.init( sourceMethod, messager, typeFactory );
                    }
                }
            }
            return sourceMethod;
        }
    }

    //CHECKSTYLE:OFF
    private SourceMethod( Type declaringMapper, ExecutableElement executable, List<Parameter> parameters,
                         Type returnType, List<Type> exceptionTypes, Map<String, List<Mapping>> mappings,
                         IterableMapping iterableMapping, MapMapping mapMapping, Types typeUtils,
                         TypeFactory typeFactory, Messager messager, MapperConfig config) {
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
        this.typeFactory = typeFactory;
        this.messager = messager;
        this.config = config;
    }
    //CHECKSTYLE:ON

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

    @Override
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
     * @return the {@link Mapping}s configured for this method, keyed by target property name. Only for enum mapping
     * methods a target will be mapped by several sources.
     */
    public Map<String, List<Mapping>> getMappings() {
        return mappings;
    }

    public Mapping getSingleMappingByTargetPropertyName(String targetPropertyName) {
        List<Mapping> all = mappings.get( targetPropertyName );
        return all != null ? all.iterator().next() : null;
    }

    public void setMappings(Map<String, List<Mapping>> mappings) {
        this.mappings = mappings;
    }

    public IterableMapping getIterableMapping() {
        return iterableMapping;
    }

    public void setIterableMapping(IterableMapping iterableMapping) {
        this.iterableMapping = iterableMapping;
    }

    public MapMapping getMapMapping() {
        return mapMapping;
    }

    public void setMapMapping(MapMapping mapMapping) {
        this.mapMapping = mapMapping;
    }

    public boolean reverses(SourceMethod method) {
        return getSourceParameters().size() == 1 && method.getSourceParameters().size() == 1
            && equals( getSourceParameters().iterator().next().getType(), method.getResultType() )
            && equals( getResultType(), method.getSourceParameters().iterator().next().getType() );
    }


    public boolean isSame(SourceMethod method) {
        return getSourceParameters().size() == 1 && method.getSourceParameters().size() == 1
            && equals( getSourceParameters().iterator().next().getType(),
                    method.getSourceParameters().iterator().next().getType())
            && equals( getResultType(), method.getResultType() );
    }

    public boolean isSimilar(SourceMethod method) {
        Map<Type, Integer> test = new HashMap<Type, Integer>();

        // check how many times a type occurs
        for (Parameter sourceParam : method.getSourceParameters() ) {
            Type sourceType = sourceParam.getType();
            if ( !test.containsKey( sourceType ) ) {
                test.put( sourceType, 0 );
            }
            increase( sourceType, test );
        }

        // check if this method also contains the same time each parameter type.
        for (Parameter sourceParam : getSourceParameters() ) {
            Type sourceType = sourceParam.getType();
            if ( !test.containsKey( sourceType ) ) {
                // method contains a different parameter type than this
                return false;
            }
            decrease( sourceType, test );
        }

        // now, if they match they should have the same source parameter types each
        for ( Integer count : test.values() ) {
            if ( count != 0 ) {
                return false;
            }
        }

        // finally check the return type.
        return  equals( getResultType(), method.getResultType() );
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
     * Returns the {@link Mapping}s for the given source property.
     *
     * @param sourcePropertyName the source property name
     *
     * @return list of mappings
     */
    public List<Mapping> getMappingBySourcePropertyName(String sourcePropertyName) {
        List<Mapping> mappingsOfSourceProperty = new ArrayList<Mapping>();

        for ( List<Mapping> mappingOfProperty : mappings.values() ) {
            for ( Mapping mapping : mappingOfProperty ) {

                if ( isEnumMapping() ) {
                    if ( mapping.getSourceName().equals( sourcePropertyName ) ) {
                        mappingsOfSourceProperty.add( mapping );
                    }
                }
                else {
                    List<PropertyEntry> sourceEntries = mapping.getSourceReference().getPropertyEntries();

                    // there can only be a mapping if there's only one entry for a source property, so: param.property.
                    // There can be no mapping if there are more entries. So: param.property.property2
                    if ( sourceEntries.size() == 1 && sourcePropertyName.equals( sourceEntries.get( 0 ).getName() ) ) {
                        mappingsOfSourceProperty.add( mapping );
                    }
                }
            }
        }

        return mappingsOfSourceProperty;
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
     *
     * @return {@code true} if the parameter list contains a parameter annotated with {@code @TargetType}
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

    /**
     * Merges in all the mappings configured via the given inverse mapping method, giving the locally defined mappings
     * precedence.
     * @param inverseMethod
     * @param templateMethod
     */
    public void mergeWithInverseMappings(SourceMethod inverseMethod, SourceMethod templateMethod) {
        Map<String, List<Mapping>> newMappings = new HashMap<String, List<Mapping>>();

        if ( inverseMethod != null && !inverseMethod.getMappings().isEmpty() ) {
            for ( List<Mapping> lmappings : inverseMethod.getMappings().values() ) {
                for ( Mapping inverseMapping : lmappings ) {
                    Mapping reversed = inverseMapping.reverse( this, messager, typeFactory );
                    if ( reversed != null ) {
                        List<Mapping> mappingsOfProperty = newMappings.get( reversed.getTargetName() );
                        if ( mappingsOfProperty == null ) {
                            mappingsOfProperty = new ArrayList<Mapping>();
                            newMappings.put( reversed.getTargetName(), mappingsOfProperty );
                        }
                        mappingsOfProperty.add( reversed );
                    }
                }
            }
        }

        if ( templateMethod != null && !templateMethod.getMappings().isEmpty() ) {
            for ( List<Mapping> lmappings : templateMethod.getMappings().values() ) {
                for ( Mapping templateMapping : lmappings ) {
                    if ( templateMapping != null ) {
                        List<Mapping> mappingsOfProperty = newMappings.get( templateMapping.getTargetName() );
                        if ( mappingsOfProperty == null ) {
                            mappingsOfProperty = new ArrayList<Mapping>();
                            newMappings.put( templateMapping.getTargetName(), mappingsOfProperty );
                        }
                        mappingsOfProperty.add( templateMapping );
                    }
                }
            }
        }

        if ( getMappings().isEmpty() ) {
            // the mapping method is configuredByReverseMappingMethod, see SourceMethod#setMappings()
            setMappings( newMappings );
        }
        else {
            // now add all of its own mappings
            newMappings.putAll( getMappings() );
            getMappings().clear();
            // the mapping method is NOT configuredByReverseMappingMethod,
            getMappings().putAll( newMappings );
        }
    }

    @Override
    public boolean isStatic() {
        return executable.getModifiers().contains( Modifier.STATIC );
    }

    private void increase(Type key, Map<Type, Integer> test) {
        Integer count = test.get( key );
        count++;
        test.put( key, count );
    }

    private void decrease(Type key, Map<Type, Integer> test) {
        Integer count = test.get( key );
        count--;
        test.put( key, count );
    }
    /**
     *
     * @return the mapper config when this method needs to be implemented
     */
    public MapperConfig getConfig() {
        return config;
    }

}
