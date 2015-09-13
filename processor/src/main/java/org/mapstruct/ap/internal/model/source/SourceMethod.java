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
package org.mapstruct.ap.internal.model.source;

import static org.mapstruct.ap.internal.util.Collections.first;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.SourceReference.PropertyEntry;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.MapperConfiguration;
import org.mapstruct.ap.internal.util.Strings;

/**
 * Represents a mapping method with source and target type and the mappings between the properties of source and target
 * type.
 * <p>
 * A method can either be configured by itself or by another method for the inverse mapping direction (the appropriate
 * setter on {@link MappingOptions} will be called in this case).
 *
 * @author Gunnar Morling
 */
public class SourceMethod implements Method {

    private final Types typeUtils;
    private final TypeFactory typeFactory;

    private final Type declaringMapper;
    private final ExecutableElement executable;
    private final List<Parameter> parameters;
    private final Parameter mappingTargetParameter;
    private final Parameter targetTypeParameter;
    private final Type returnType;
    private final Accessibility accessibility;
    private final List<Type> exceptionTypes;
    private final MapperConfiguration config;
    private final MappingOptions mappingOptions;
    private final List<SourceMethod> prototypeMethods;
    private final Type mapperToImplement;

    private List<Parameter> sourceParameters;

    private List<String> parameterNames;

    private List<SourceMethod> applicablePrototypeMethods;

    public static class Builder {

        private Type declaringMapper = null;
        private Type definingType = null;
        private ExecutableElement executable;
        private List<Parameter> parameters;
        private Type returnType = null;
        private List<Type> exceptionTypes;
        private Map<String, List<Mapping>> mappings;
        private IterableMapping iterableMapping = null;
        private MapMapping mapMapping = null;
        private BeanMapping beanMapping = null;
        private Types typeUtils;
        private TypeFactory typeFactory = null;
        private FormattingMessager messager = null;
        private MapperConfiguration mapperConfig = null;
        private List<SourceMethod> prototypeMethods = Collections.emptyList();

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

        public Builder setBeanMapping(BeanMapping beanMapping) {
            this.beanMapping = beanMapping;
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

        public Builder setMessager(FormattingMessager messager) {
            this.messager = messager;
            return this;
        }

        public Builder setMapperConfiguration(MapperConfiguration mapperConfig) {
            this.mapperConfig = mapperConfig;
            return this;
        }

        public Builder setPrototypeMethods(List<SourceMethod> prototypeMethods) {
            this.prototypeMethods = prototypeMethods;
            return this;
        }

        public Builder setDefininingType(Type definingType) {
            this.definingType = definingType;
            return this;
        }

        public SourceMethod build() {

            MappingOptions mappingOptions
                = new MappingOptions( mappings, iterableMapping, mapMapping, beanMapping );


            SourceMethod sourceMethod = new SourceMethod(
                declaringMapper,
                executable,
                parameters,
                returnType,
                exceptionTypes,
                mappingOptions,
                typeUtils,
                typeFactory,
                mapperConfig,
                prototypeMethods,
                definingType
            );

            if ( mappings != null ) {
                for ( Map.Entry<String, List<Mapping>> entry : mappings.entrySet() ) {
                    for ( Mapping mapping : entry.getValue() ) {
                        mapping.init( sourceMethod, messager, typeFactory );
                    }
                }
            }
            return sourceMethod;
        }
    }

    @SuppressWarnings("checkstyle:parameternumber")
    private SourceMethod(Type declaringMapper, ExecutableElement executable, List<Parameter> parameters,
                         Type returnType, List<Type> exceptionTypes, MappingOptions mappingOptions, Types typeUtils,
                         TypeFactory typeFactory, MapperConfiguration config, List<SourceMethod> prototypeMethods,
                         Type mapperToImplement) {
        this.declaringMapper = declaringMapper;
        this.executable = executable;
        this.parameters = parameters;
        this.returnType = returnType;
        this.exceptionTypes = exceptionTypes;
        this.accessibility = Accessibility.fromModifiers( executable.getModifiers() );

        this.mappingOptions = mappingOptions;

        this.mappingTargetParameter = determineMappingTargetParameter( parameters );
        this.targetTypeParameter = determineTargetTypeParameter( parameters );

        this.typeUtils = typeUtils;
        this.typeFactory = typeFactory;
        this.config = config;
        this.prototypeMethods = prototypeMethods;
        this.mapperToImplement = mapperToImplement;
    }

    private Parameter determineMappingTargetParameter(Iterable<Parameter> parameters) {
        for ( Parameter parameter : parameters ) {
            if ( parameter.isMappingTarget() ) {
                return parameter;
            }
        }

        return null;
    }

    private Parameter determineTargetTypeParameter(Iterable<Parameter> parameters) {
        for ( Parameter parameter : parameters ) {
            if ( parameter.isTargetType() ) {
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
        if ( sourceParameters == null ) {
            sourceParameters = new ArrayList<Parameter>();

            for ( Parameter parameter : parameters ) {
                if ( !parameter.isMappingTarget() && !parameter.isTargetType() ) {
                    sourceParameters.add( parameter );
                }
            }
        }

        return sourceParameters;
    }

    @Override
    public List<String> getParameterNames() {
        if ( parameterNames == null ) {
            parameterNames = new ArrayList<String>( parameters.size() );

            for ( Parameter parameter : parameters ) {
                parameterNames.add( parameter.getName() );
            }
        }

        return parameterNames;
    }

    @Override
    public Type getResultType() {
        return mappingTargetParameter != null ? mappingTargetParameter.getType() : returnType;
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

    public Mapping getSingleMappingByTargetPropertyName(String targetPropertyName) {
        List<Mapping> all = mappingOptions.getMappings().get( targetPropertyName );
        return all != null ? first( all ) : null;
    }

    public boolean reverses(SourceMethod method) {
        return getSourceParameters().size() == 1 && method.getSourceParameters().size() == 1
            && equals( first( getSourceParameters() ).getType(), method.getResultType() )
            && equals( getResultType(), first( method.getSourceParameters() ).getType() );
    }

    public boolean isSame(SourceMethod method) {
        return getSourceParameters().size() == 1 && method.getSourceParameters().size() == 1
            && equals( first( getSourceParameters() ).getType(),
                first( method.getSourceParameters() ).getType() )
            && equals( getResultType(), method.getResultType() );
    }

    public boolean canInheritFrom(SourceMethod method) {
        return isMapMapping() == method.isMapMapping()
            && isIterableMapping() == method.isIterableMapping()
            && isEnumMapping() == method.isEnumMapping()
            && getResultType().isAssignableTo( method.getResultType() )
            && allParametersAreAssignable( getSourceParameters(), method.getSourceParameters() );
    }

    @Override
    public Parameter getMappingTargetParameter() {
        return mappingTargetParameter;
    }

    @Override
    public Parameter getTargetTypeParameter() {
        return targetTypeParameter;
    }

    public boolean isIterableMapping() {
        return getSourceParameters().size() == 1 && first( getSourceParameters() ).getType().isIterableType()
            && getResultType().isIterableType();
    }

    public boolean isMapMapping() {
        return getSourceParameters().size() == 1 && first( getSourceParameters() ).getType().isMapType()
            && getResultType().isMapType();
    }

    public boolean isEnumMapping() {
        return getSourceParameters().size() == 1 && first( getSourceParameters() ).getType().isEnumType()
            && getResultType().isEnumType();
    }

    private boolean equals(Object o1, Object o2) {
        return (o1 == null && o2 == null) || (o1 != null) && o1.equals( o2 );
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

        for ( List<Mapping> mappingOfProperty : mappingOptions.getMappings().values() ) {
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
                    if ( sourceEntries.size() == 1 && sourcePropertyName.equals( first( sourceEntries ).getName() ) ) {
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

    public List<SourceMethod> getApplicablePrototypeMethods() {
        if ( applicablePrototypeMethods == null ) {
            applicablePrototypeMethods = new ArrayList<SourceMethod>();

            for ( SourceMethod prototype : prototypeMethods ) {
                if ( canInheritFrom( prototype ) ) {
                    applicablePrototypeMethods.add( prototype );
                }
            }
        }

        return applicablePrototypeMethods;
    }

    private static boolean allParametersAreAssignable(List<Parameter> fromParams, List<Parameter> toParams) {
        if ( fromParams.size() == toParams.size() ) {
            Set<Parameter> unaccountedToParams = new HashSet<Parameter>( toParams );

            for ( Parameter fromParam : fromParams ) {
                // each fromParam needs at least one match, and all toParam need to be accounted for at the end
                boolean hasMatch = false;
                for ( Parameter toParam : toParams ) {
                    if ( fromParam.getType().isAssignableTo( toParam.getType() ) ) {
                        unaccountedToParams.remove( toParam );
                        hasMatch = true;
                    }
                }

                if ( !hasMatch ) {
                    return false;
                }
            }

            return unaccountedToParams.isEmpty();
        }

        return false;
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
        MethodMatcher matcher = new MethodMatcher( typeUtils, typeFactory, this );
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

    public MappingOptions getMappingOptions() {
        return mappingOptions;
    }

    @Override
    public boolean isStatic() {
        return executable.getModifiers().contains( Modifier.STATIC );
    }

    @Override
    public boolean isDefault() {
        return Executables.isDefaultMethod( executable );
    }

    @Override
    public Type getDefiningType() {
        return mapperToImplement;
    }

    @Override
    public MapperConfiguration getMapperConfiguration() {
        return config;
    }

    @Override
    public boolean isLifecycleCallbackMethod() {
        return Executables.isLifecycleCallbackMethod( getExecutable() );
    }

    public boolean isAfterMappingMethod() {
        return Executables.isAfterMappingMethod( getExecutable() );
    }

    public boolean isBeforeMappingMethod() {
        return Executables.isBeforeMappingMethod( getExecutable() );
    }

    @Override
    public boolean isUpdateMethod() {
        return getMappingTargetParameter() != null;
    }
}
