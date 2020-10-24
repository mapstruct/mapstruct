/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import org.mapstruct.ap.internal.util.TypeUtils;

import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.gem.ObjectFactoryGem;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.model.source.MappingMethodUtils.isEnumMapping;
import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * Represents a mapping method with source and target type and the mappings between the properties of source and target
 * type.
 * <p>
 * A method can either be configured by itself or by another method for the inverse mapping direction (the appropriate
 * setter on {@link MappingMethodOptions} will be called in this case).
 *
 * @author Gunnar Morling
 */
public class SourceMethod implements Method {

    private final TypeUtils typeUtils;
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
    private final MappingMethodOptions mappingMethodOptions;
    private final List<SourceMethod> prototypeMethods;
    private final Type mapperToImplement;

    private final List<Parameter> sourceParameters;
    private final List<Parameter> contextParameters;
    private final ParameterProvidedMethods contextProvidedMethods;
    private final List<Type> typeParameters;

    private List<String> parameterNames;

    private List<SourceMethod> applicablePrototypeMethods;
    private List<SourceMethod> applicableReversePrototypeMethods;

    private Boolean isValueMapping;
    private Boolean isIterableMapping;
    private Boolean isMapMapping;
    private Boolean isStreamMapping;
    private final boolean hasObjectFactoryAnnotation;

    private final boolean verboseLogging;

    public static class Builder {

        private Type declaringMapper = null;
        private Type definingType = null;
        private ExecutableElement executable;
        private List<Parameter> parameters;
        private Type returnType = null;
        private List<Type> exceptionTypes;
        private Set<MappingOptions> mappings;
        private IterableMappingOptions iterableMapping = null;
        private MapMappingOptions mapMapping = null;
        private BeanMappingOptions beanMapping = null;
        private TypeUtils typeUtils;
        private TypeFactory typeFactory = null;
        private MapperOptions mapper = null;
        private List<SourceMethod> prototypeMethods = Collections.emptyList();
        private List<ValueMappingOptions> valueMappings;
        private EnumMappingOptions enumMappingOptions;
        private ParameterProvidedMethods contextProvidedMethods;
        private List<Type> typeParameters;

        private boolean verboseLogging;

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

        public Builder setMappingOptions(Set<MappingOptions> mappings) {
            this.mappings = mappings;
            return this;
        }

        public Builder setIterableMappingOptions(IterableMappingOptions iterableMapping) {
            this.iterableMapping = iterableMapping;
            return this;
        }

        public Builder setMapMappingOptions(MapMappingOptions mapMapping) {
            this.mapMapping = mapMapping;
            return this;
        }

        public Builder setBeanMappingOptions(BeanMappingOptions beanMapping) {
            this.beanMapping = beanMapping;
            return this;
        }

        public Builder setValueMappingOptionss(List<ValueMappingOptions> valueMappings) {
            this.valueMappings = valueMappings;
            return this;
        }

        public Builder setEnumMappingOptions(EnumMappingOptions enumMappingOptions) {
            this.enumMappingOptions = enumMappingOptions;
            return this;
        }

        public Builder setTypeUtils(TypeUtils typeUtils) {
            this.typeUtils = typeUtils;
            return this;
        }

        public Builder setTypeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public Builder setMapper(MapperOptions mapper) {
            this.mapper = mapper;
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

        public Builder setVerboseLogging(boolean verboseLogging) {
            this.verboseLogging = verboseLogging;
            return this;
        }

        public SourceMethod build() {

            if ( mappings == null ) {
                mappings = Collections.emptySet();
            }

            MappingMethodOptions mappingMethodOptions = new MappingMethodOptions(
                mapper,
                mappings,
                iterableMapping,
                mapMapping,
                beanMapping,
                enumMappingOptions,
                valueMappings
            );

            this.typeParameters = this.executable.getTypeParameters()
                .stream()
                .map( Element::asType )
                .map( typeFactory::getType )
                .collect( Collectors.toList() );

            return new SourceMethod( this, mappingMethodOptions );
        }
    }

    private SourceMethod(Builder builder, MappingMethodOptions mappingMethodOptions) {
        this.declaringMapper = builder.declaringMapper;
        this.executable = builder.executable;
        this.parameters = builder.parameters;
        this.returnType = builder.returnType;
        this.exceptionTypes = builder.exceptionTypes;
        this.accessibility = Accessibility.fromModifiers( builder.executable.getModifiers() );

        this.mappingMethodOptions = mappingMethodOptions;

        this.sourceParameters = Parameter.getSourceParameters( parameters );
        this.contextParameters = Parameter.getContextParameters( parameters );
        this.contextProvidedMethods = builder.contextProvidedMethods;
        this.typeParameters = builder.typeParameters;

        this.mappingTargetParameter = Parameter.getMappingTargetParameter( parameters );
        this.targetTypeParameter = Parameter.getTargetTypeParameter( parameters );
        this.hasObjectFactoryAnnotation = ObjectFactoryGem.instanceOn( executable ) != null;
        this.isObjectFactory = determineIfIsObjectFactory();

        this.typeUtils = builder.typeUtils;
        this.typeFactory = builder.typeFactory;
        this.prototypeMethods = builder.prototypeMethods;
        this.mapperToImplement = builder.definingType;

        this.verboseLogging = builder.verboseLogging;
    }

    private boolean determineIfIsObjectFactory() {
        boolean hasNoSourceParameters = getSourceParameters().isEmpty();
        boolean hasNoMappingTargetParam = getMappingTargetParameter() == null;
        return !isLifecycleCallbackMethod() && !returnType.isVoid()
            && hasNoMappingTargetParam
            && ( hasObjectFactoryAnnotation || hasNoSourceParameters );
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
            List<String> names = new ArrayList<>( parameters.size() );

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

    public boolean inverses(SourceMethod method) {
        return method.getDeclaringMapper() == null
            && method.isAbstract()
            && getSourceParameters().size() == 1 && method.getSourceParameters().size() == 1
            && getMappingSourceType().isAssignableTo( method.getResultType() )
            && getResultType().isAssignableTo( first( method.getSourceParameters() ).getType() );
    }

    public boolean canInheritFrom(SourceMethod method) {
        return method.getDeclaringMapper() == null
            && method.isAbstract()
            && isMapMapping() == method.isMapMapping()
            && isIterableMapping() == method.isIterableMapping()
            && isEnumMapping( this ) == isEnumMapping( method )
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
                && getMappingSourceType().isIterableType()
                && getResultType().isIterableType();
        }
        return isIterableMapping;
    }

    public boolean isStreamMapping() {
        if ( isStreamMapping == null ) {
            isStreamMapping = getSourceParameters().size() == 1
                && ( getMappingSourceType().isIterableType() && getResultType().isStreamType()
                    || getMappingSourceType().isStreamType() && getResultType().isIterableType()
                    || getMappingSourceType().isStreamType() && getResultType().isStreamType() );
        }
        return isStreamMapping;
    }

    public boolean isMapMapping() {
        if ( isMapMapping == null ) {
            isMapMapping = getSourceParameters().size() == 1
                && getMappingSourceType().isMapType()
                && getResultType().isMapType();
        }
        return isMapMapping;
    }

    /**
     * Enum Mapping was realized with @Mapping in stead of @ValueMapping. @Mapping is no longer
     * supported.
     *
     * @return true when @Mapping is used in stead of @ValueMapping
     */
    public boolean isRemovedEnumMapping() {
        return MappingMethodUtils.isEnumMapping( this );
    }

    /**
     * The default enum mapping (no mappings specified) will from now on be handled as a value mapping. If there
     * are any @Mapping / @Mappings defined on the method, then the deprecated enum behavior should be executed.
     *
     * @return whether (true) or not (false) to execute value mappings
     */
    public boolean isValueMapping() {

        if ( isValueMapping == null ) {
            isValueMapping = isEnumMapping( this ) && mappingMethodOptions.getMappings().isEmpty();
        }
        return isValueMapping;
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

    public List<SourceMethod> getApplicablePrototypeMethods() {
        if ( applicablePrototypeMethods == null ) {
            applicablePrototypeMethods = new ArrayList<>();

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
            applicableReversePrototypeMethods = new ArrayList<>();

            for ( SourceMethod prototype : prototypeMethods ) {
                if ( inverses( prototype ) ) {
                    applicableReversePrototypeMethods.add( prototype );
                }
            }
        }

        return applicableReversePrototypeMethods;
    }

    private static boolean allParametersAreAssignable(List<Parameter> fromParams, List<Parameter> toParams) {
        if ( fromParams.size() == toParams.size() ) {
            Set<Parameter> unaccountedToParams = new HashSet<>( toParams );

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
        return parameters.stream().anyMatch( Parameter::isTargetType );
    }

    @Override
    public List<Type> getThrownTypes() {
        return exceptionTypes;
    }

    @Override
    public MappingMethodOptions getOptions() {
        return mappingMethodOptions;
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

    public boolean hasObjectFactoryAnnotation() {
        return hasObjectFactoryAnnotation;
    }

    @Override
    public List<Type> getTypeParameters() {
        return this.typeParameters;
    }

    @Override
    public String describe() {
        if ( verboseLogging ) {
            return toString();
        }
        else {
            String mapper = declaringMapper != null ? declaringMapper.getName() + "." : "";
            String sourceTypes = getParameters().stream()
                .map( Parameter::describe )
                .collect( Collectors.joining( ", " ) );
            return getResultType().describe() + " " + mapper + getName() + "(" + sourceTypes + ")";
        }
    }
}
