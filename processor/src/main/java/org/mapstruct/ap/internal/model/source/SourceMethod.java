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
import org.mapstruct.ap.internal.prism.ObjectFactoryPrism;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
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
    private final boolean isObjectFactory;
    private final Type returnType;
    private final Accessibility accessibility;
    private final List<Type> exceptionTypes;
    private final MapperConfiguration config;
    private final MappingOptions mappingOptions;
    private final List<SourceMethod> prototypeMethods;
    private final Type mapperToImplement;

    private final List<Parameter> sourceParameters;
    private final List<Parameter> contextParameters;
    private final ParameterProvidedMethods contextProvidedMethods;

    private List<String> parameterNames;

    private List<SourceMethod> applicablePrototypeMethods;
    private List<SourceMethod> applicableReversePrototypeMethods;

    private Boolean isBeanMapping;
    private Boolean isEnumMapping;
    private Boolean isValueMapping;
    private Boolean isIterableMapping;
    private Boolean isMapMapping;
    private Boolean isStreamMapping;

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
        private AccessorNamingUtils accessorNaming = null;
        private FormattingMessager messager = null;
        private MapperConfiguration mapperConfig = null;
        private List<SourceMethod> prototypeMethods = Collections.emptyList();
        private List<ValueMapping> valueMappings;
        private ParameterProvidedMethods contextProvidedMethods;

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

        public Builder setValueMappings(List<ValueMapping> valueMappings) {
            this.valueMappings = valueMappings;
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

        public Builder setAccessorNaming(AccessorNamingUtils accessorNaming) {
            this.accessorNaming = accessorNaming;
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

        public Builder setContextProvidedMethods(ParameterProvidedMethods contextProvidedMethods) {
            this.contextProvidedMethods = contextProvidedMethods;
            return this;
        }

        public SourceMethod build() {

            MappingOptions mappingOptions =
                    new MappingOptions( mappings, iterableMapping, mapMapping, beanMapping, valueMappings, false );

            SourceMethod sourceMethod = new SourceMethod( this, mappingOptions );

            if ( mappings != null ) {
                for ( Map.Entry<String, List<Mapping>> entry : mappings.entrySet() ) {
                    for ( Mapping mapping : entry.getValue() ) {
                        mapping.init( sourceMethod, messager, typeFactory, accessorNaming );
                    }
                }
            }
            return sourceMethod;
        }
    }

    private SourceMethod(Builder builder, MappingOptions mappingOptions) {
        this.declaringMapper = builder.declaringMapper;
        this.executable = builder.executable;
        this.parameters = builder.parameters;
        this.returnType = builder.returnType;
        this.exceptionTypes = builder.exceptionTypes;
        this.accessibility = Accessibility.fromModifiers( builder.executable.getModifiers() );

        this.mappingOptions = mappingOptions;

        this.sourceParameters = Parameter.getSourceParameters( parameters );
        this.contextParameters = Parameter.getContextParameters( parameters );
        this.contextProvidedMethods = builder.contextProvidedMethods;

        this.mappingTargetParameter = Parameter.getMappingTargetParameter( parameters );
        this.targetTypeParameter = Parameter.getTargetTypeParameter( parameters );
        this.isObjectFactory = determineIfIsObjectFactory( executable );

        this.typeUtils = builder.typeUtils;
        this.typeFactory = builder.typeFactory;
        this.config = builder.mapperConfig;
        this.prototypeMethods = builder.prototypeMethods;
        this.mapperToImplement = builder.definingType;
    }

    private boolean determineIfIsObjectFactory(ExecutableElement executable) {
        boolean hasFactoryAnnotation = ObjectFactoryPrism.getInstanceOn( executable ) != null;
        boolean hasNoSourceParameters = getSourceParameters().isEmpty();
        boolean hasNoMappingTargetParam = getMappingTargetParameter() == null;
        return !isLifecycleCallbackMethod() && !returnType.isVoid()
            && hasNoMappingTargetParam
            && ( hasFactoryAnnotation || hasNoSourceParameters );
    }

    @Override
    public Type getDeclaringMapper() {
        return declaringMapper;
    }

    @Override
    public ExecutableElement getExecutable() {
        return executable;
    }

    @Override
    public String getName() {
        return executable.getSimpleName().toString();
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public List<Parameter> getSourceParameters() {
        return sourceParameters;
    }

    @Override
    public List<Parameter> getContextParameters() {
        return contextParameters;
    }

    @Override
    public ParameterProvidedMethods getContextProvidedMethods() {
        return contextProvidedMethods;
    }

    @Override
    public List<String> getParameterNames() {
        if ( parameterNames == null ) {
            List<String> names = new ArrayList<String>( parameters.size() );

            for ( Parameter parameter : parameters ) {
                names.add( parameter.getName() );
            }

            parameterNames = Collections.unmodifiableList( names );
        }

        return parameterNames;
    }

    @Override
    public Type getResultType() {
        return mappingTargetParameter != null ? mappingTargetParameter.getType() : returnType;
    }

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
        return method.getDeclaringMapper() == null
            && method.isAbstract()
            && getSourceParameters().size() == 1 && method.getSourceParameters().size() == 1
            && first( getSourceParameters() ).getType().isAssignableTo( method.getResultType() )
            && getResultType().isAssignableTo( first( method.getSourceParameters() ).getType() );
    }

    public boolean isSame(SourceMethod method) {
        return getSourceParameters().size() == 1 && method.getSourceParameters().size() == 1
            && equals( first( getSourceParameters() ).getType(), first( method.getSourceParameters() ).getType() )
            && equals( getResultType(), method.getResultType() );
    }

    public boolean canInheritFrom(SourceMethod method) {
        return method.getDeclaringMapper() == null
            && method.isAbstract()
            && isMapMapping() == method.isMapMapping()
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
    public boolean isObjectFactory() {
        return isObjectFactory;
    }

    @Override
    public Parameter getTargetTypeParameter() {
        return targetTypeParameter;
    }

    public boolean isIterableMapping() {
        if ( isIterableMapping == null ) {
            isIterableMapping = getSourceParameters().size() == 1
                && first( getSourceParameters() ).getType().isIterableType()
                && getResultType().isIterableType();
        }
        return isIterableMapping;
    }

    public boolean isStreamMapping() {
        if ( isStreamMapping == null ) {
            isStreamMapping = getSourceParameters().size() == 1
                && ( first( getSourceParameters() ).getType().isIterableType() && getResultType().isStreamType()
                    || first( getSourceParameters() ).getType().isStreamType() && getResultType().isIterableType()
                    || first( getSourceParameters() ).getType().isStreamType() && getResultType().isStreamType() );
        }
        return isStreamMapping;
    }

    public boolean isMapMapping() {
        if ( isMapMapping == null ) {
            isMapMapping = getSourceParameters().size() == 1
                && first( getSourceParameters() ).getType().isMapType()
                && getResultType().isMapType();
        }
        return isMapMapping;
    }

    public boolean isEnumMapping() {
        if ( isEnumMapping == null ) {
            isEnumMapping = MappingMethodUtils.isEnumMapping( this );
        }
        return isEnumMapping;
    }

    /**
     * The default enum mapping (no mappings specified) will from now on be handled as a value mapping. If there
     * are any @Mapping / @Mappings defined on the method, then the deprecated enum behavior should be executed.
     *
     * @return whether (true) or not (false) to execute value mappings
     */
    public boolean isValueMapping() {

        if ( isValueMapping == null ) {
            isValueMapping = isEnumMapping() && mappingOptions.getMappings().isEmpty();
        }
        return isValueMapping;
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

    public List<SourceMethod> getApplicableReversePrototypeMethods() {
        if ( applicableReversePrototypeMethods == null ) {
            applicableReversePrototypeMethods = new ArrayList<SourceMethod>();

            for ( SourceMethod prototype : prototypeMethods ) {
                if ( reverses( prototype ) ) {
                    applicableReversePrototypeMethods.add( prototype );
                }
            }
        }

        return applicableReversePrototypeMethods;
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

    @Override
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

    /**
     * @return returns true for interface methods (see jls 9.4) lacking a default or static modifier and for abstract
     * methods
     */
    public boolean isAbstract() {
        return executable.getModifiers().contains( Modifier.ABSTRACT );
    }

    @Override
    public boolean isUpdateMethod() {
        return getMappingTargetParameter() != null;
    }
}
