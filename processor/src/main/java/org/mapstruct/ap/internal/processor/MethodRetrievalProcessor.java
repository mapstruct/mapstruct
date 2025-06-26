/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;

import org.mapstruct.ap.internal.gem.BeanMappingGem;
import org.mapstruct.ap.internal.gem.ConditionGem;
import org.mapstruct.ap.internal.gem.IgnoredGem;
import org.mapstruct.ap.internal.gem.IgnoredListGem;
import org.mapstruct.ap.internal.gem.IterableMappingGem;
import org.mapstruct.ap.internal.gem.MapMappingGem;
import org.mapstruct.ap.internal.gem.MappingGem;
import org.mapstruct.ap.internal.gem.MappingsGem;
import org.mapstruct.ap.internal.gem.ObjectFactoryGem;
import org.mapstruct.ap.internal.gem.SourcePropertyNameGem;
import org.mapstruct.ap.internal.gem.SubclassMappingGem;
import org.mapstruct.ap.internal.gem.SubclassMappingsGem;
import org.mapstruct.ap.internal.gem.TargetPropertyNameGem;
import org.mapstruct.ap.internal.gem.ValueMappingGem;
import org.mapstruct.ap.internal.gem.ValueMappingsGem;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.BeanMappingOptions;
import org.mapstruct.ap.internal.model.source.ConditionOptions;
import org.mapstruct.ap.internal.model.source.EnumMappingOptions;
import org.mapstruct.ap.internal.model.source.IterableMappingOptions;
import org.mapstruct.ap.internal.model.source.MapMappingOptions;
import org.mapstruct.ap.internal.model.source.MapperOptions;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.SubclassMappingOptions;
import org.mapstruct.ap.internal.model.source.SubclassValidator;
import org.mapstruct.ap.internal.model.source.ValueMappingOptions;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.AnnotationProcessingException;
import org.mapstruct.ap.internal.util.ElementUtils;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.MetaAnnotations;
import org.mapstruct.ap.internal.util.RepeatableAnnotations;
import org.mapstruct.ap.internal.util.TypeUtils;
import org.mapstruct.ap.spi.EnumTransformationStrategy;

/**
 * A {@link ModelElementProcessor} which retrieves a list of {@link SourceMethod}s
 * representing all the mapping methods of the given bean mapper type as well as
 * all referenced mapper methods declared by other mappers referenced by the
 * current mapper.
 *
 * @author Gunnar Morling
 */
public class MethodRetrievalProcessor implements ModelElementProcessor<Void, List<SourceMethod>> {

    private static final String MAPPING_FQN = "org.mapstruct.Mapping";
    private static final String MAPPINGS_FQN = "org.mapstruct.Mappings";
    private static final String SUB_CLASS_MAPPING_FQN = "org.mapstruct.SubclassMapping";
    private static final String SUB_CLASS_MAPPINGS_FQN = "org.mapstruct.SubclassMappings";
    private static final String VALUE_MAPPING_FQN = "org.mapstruct.ValueMapping";
    private static final String VALUE_MAPPINGS_FQN = "org.mapstruct.ValueMappings";
    private static final String CONDITION_FQN = "org.mapstruct.Condition";
    private static final String IGNORED_FQN = "org.mapstruct.Ignored";
    private static final String IGNORED_LIST_FQN = "org.mapstruct.IgnoredList";
    private FormattingMessager messager;
    private TypeFactory typeFactory;
    private AccessorNamingUtils accessorNaming;
    private Map<String, EnumTransformationStrategy> enumTransformationStrategies;
    private TypeUtils typeUtils;
    private ElementUtils elementUtils;
    private Options options;

    @Override
    public List<SourceMethod> process(ProcessorContext context, TypeElement mapperTypeElement, Void sourceModel) {
        this.messager = context.getMessager();
        this.typeFactory = context.getTypeFactory();
        this.accessorNaming = context.getAccessorNaming();
        this.typeUtils = context.getTypeUtils();
        this.elementUtils = context.getElementUtils();
        this.enumTransformationStrategies = context.getEnumTransformationStrategies();
        this.options = context.getOptions();

        this.messager.note( 0, Message.PROCESSING_NOTE, mapperTypeElement );

        MapperOptions mapperOptions = MapperOptions.getInstanceOn( mapperTypeElement, context.getOptions() );

        if ( mapperOptions.hasMapperConfig() ) {
            this.messager.note( 0, Message.CONFIG_NOTE, mapperOptions.mapperConfigType().asElement().getSimpleName() );
        }

        if ( !mapperOptions.isValid() ) {
            throw new AnnotationProcessingException(
                "Couldn't retrieve @Mapper annotation",
                mapperTypeElement,
                mapperOptions.getAnnotationMirror() );
        }

        List<SourceMethod> prototypeMethods = retrievePrototypeMethods( mapperTypeElement, mapperOptions );
        return retrieveMethods(
            typeFactory.getType( mapperTypeElement.asType() ),
            mapperTypeElement,
            mapperOptions,
            prototypeMethods
        );
    }

    @Override
    public int getPriority() {
        return 1;
    }

    private List<SourceMethod> retrievePrototypeMethods(TypeElement mapperTypeElement,
            MapperOptions mapperAnnotation) {
        if ( !mapperAnnotation.hasMapperConfig() ) {
            return Collections.emptyList();
        }

        TypeElement typeElement = asTypeElement( mapperAnnotation.mapperConfigType() );
        List<SourceMethod> methods = new ArrayList<>();
        for ( ExecutableElement executable : elementUtils.getAllEnclosedExecutableElements( typeElement ) ) {

            if ( executable.isDefault() || executable.getModifiers().contains( Modifier.STATIC ) ) {
                // skip the default and static methods because these are not prototypes.
                continue;
            }

            ExecutableType methodType = typeFactory.getMethodType( mapperAnnotation.mapperConfigType(), executable );
            List<Parameter> parameters = typeFactory.getParameters( methodType, executable );
            boolean containsTargetTypeParameter = SourceMethod.containsTargetTypeParameter( parameters );

            // prototype methods don't have prototypes themselves
            List<SourceMethod> prototypeMethods = Collections.emptyList();

            SourceMethod method =
                getMethodRequiringImplementation(
                    methodType,
                    executable,
                    parameters,
                    containsTargetTypeParameter,
                    mapperAnnotation,
                    prototypeMethods,
                    mapperTypeElement
                );

            if ( method != null ) {
                methods.add( method );
            }
        }

        return methods;
    }

    /**
     * Retrieves the mapping methods declared by the given mapper type.
     *
     * @param usedMapperType The type of interest (either the mapper to implement, a used mapper via @uses annotation,
     *                   or a parameter type annotated with @Context)
     * @param mapperToImplement the top level type (mapper) that requires implementation
     * @param mapperOptions the mapper config
     * @param prototypeMethods prototype methods defined in mapper config type
     * @return All mapping methods declared by the given type
     */
    private List<SourceMethod> retrieveMethods(Type usedMapperType, TypeElement mapperToImplement,
                                               MapperOptions mapperOptions, List<SourceMethod> prototypeMethods) {
        List<SourceMethod> methods = new ArrayList<>();

        TypeElement usedMapper = usedMapperType.getTypeElement();
        for ( ExecutableElement executable : elementUtils.getAllEnclosedExecutableElements( usedMapper ) ) {
            SourceMethod method = getMethod(
                usedMapperType,
                executable,
                mapperToImplement,
                mapperOptions,
                prototypeMethods );

            if ( method != null ) {
                methods.add( method );
            }
        }

        //Add all methods of used mappers in order to reference them in the aggregated model
        if ( usedMapper.equals( mapperToImplement ) ) {
            for ( DeclaredType mapper : mapperOptions.uses() ) {
                TypeElement usesMapperElement = asTypeElement( mapper );
                if ( !mapperToImplement.equals( usesMapperElement ) ) {
                    methods.addAll( retrieveMethods(
                        typeFactory.getType( mapper ),
                        mapperToImplement,
                        mapperOptions,
                        prototypeMethods ) );
                }
                else {
                    messager.printMessage(
                        mapperToImplement,
                        mapperOptions.getAnnotationMirror(),
                        Message.RETRIEVAL_MAPPER_USES_CYCLE,
                        mapperToImplement
                    );
                }
            }
        }

        return methods;
    }

    private TypeElement asTypeElement(DeclaredType type) {
        return (TypeElement) type.asElement();
    }

    private SourceMethod getMethod(Type usedMapperType,
                                   ExecutableElement method,
                                   TypeElement mapperToImplement,
                                   MapperOptions mapperOptions,
                                   List<SourceMethod> prototypeMethods) {
        TypeElement usedMapper = usedMapperType.getTypeElement();
        ExecutableType methodType = typeFactory.getMethodType( (DeclaredType) usedMapperType.getTypeMirror(), method );
        List<Parameter> parameters = typeFactory.getParameters( methodType, method );
        Type returnType = typeFactory.getReturnType( methodType );

        boolean methodRequiresImplementation = method.getModifiers().contains( Modifier.ABSTRACT );
        boolean containsTargetTypeParameter = SourceMethod.containsTargetTypeParameter( parameters );

        //add method with property mappings if an implementation needs to be generated
        if ( ( usedMapper.equals( mapperToImplement ) ) && methodRequiresImplementation ) {
            return getMethodRequiringImplementation( methodType,
                method,
                parameters,
                containsTargetTypeParameter,
                mapperOptions,
                prototypeMethods,
                mapperToImplement );
        }
        // otherwise add reference to existing mapper method
        else if ( isValidReferencedMethod( parameters ) || isValidFactoryMethod( method, parameters, returnType )
            || isValidLifecycleCallbackMethod( method )
            || isValidPresenceCheckMethod( method, parameters, returnType ) ) {
            return getReferencedMethod( usedMapper, methodType, method, mapperToImplement, parameters );
        }
        else {
            return null;
        }
    }

    private SourceMethod getMethodRequiringImplementation(ExecutableType methodType, ExecutableElement method,
            List<Parameter> parameters,
            boolean containsTargetTypeParameter,
            MapperOptions mapperOptions,
            List<SourceMethod> prototypeMethods,
            TypeElement mapperToImplement) {
        Type returnType = typeFactory.getReturnType( methodType );
        List<Type> exceptionTypes = typeFactory.getThrownTypes( methodType );
        List<Parameter> sourceParameters = Parameter.getSourceParameters( parameters );
        List<Parameter> contextParameters = Parameter.getContextParameters( parameters );
        Parameter targetParameter = extractTargetParameter( parameters );
        Type resultType = selectResultType( returnType, targetParameter );

        boolean isValid = checkParameterAndReturnType(
            method,
            sourceParameters,
            targetParameter,
            contextParameters,
            resultType,
            returnType,
            containsTargetTypeParameter
        );

        if ( !isValid ) {
            return null;
        }

        ParameterProvidedMethods contextProvidedMethods =
            retrieveContextProvidedMethods( contextParameters, mapperToImplement, mapperOptions );

        BeanMappingOptions beanMappingOptions = BeanMappingOptions.getInstanceOn(
            BeanMappingGem.instanceOn( method ),
            mapperOptions,
            method,
            messager,
            typeUtils,
            typeFactory );

        Set<MappingOptions> mappingOptions = getMappings( method, beanMappingOptions );

        IterableMappingOptions iterableMappingOptions = IterableMappingOptions.fromGem(
            IterableMappingGem.instanceOn( method ),
            mapperOptions,
            method,
            messager,
            typeUtils
        );

        MapMappingOptions mapMappingOptions = MapMappingOptions.fromGem(
            MapMappingGem.instanceOn( method ),
            mapperOptions,
            method,
            messager,
            typeUtils
        );

        EnumMappingOptions enumMappingOptions = EnumMappingOptions.getInstanceOn(
            method,
            mapperOptions,
            enumTransformationStrategies,
            messager
        );

        // We want to get as much error reporting as possible.
        // If targetParameter is not null it means we have an update method
        SubclassValidator subclassValidator = new SubclassValidator( messager, typeUtils );
        Set<SubclassMappingOptions> subclassMappingOptions = getSubclassMappings(
            sourceParameters,
            targetParameter != null ? null : resultType,
            method,
            beanMappingOptions,
            subclassValidator
        );

        return new SourceMethod.Builder()
            .setExecutable( method )
            .setParameters( parameters )
            .setReturnType( returnType )
            .setExceptionTypes( exceptionTypes )
            .setMapper( mapperOptions )
            .setBeanMappingOptions( beanMappingOptions )
            .setMappingOptions( mappingOptions )
            .setIterableMappingOptions( iterableMappingOptions )
            .setMapMappingOptions( mapMappingOptions )
            .setValueMappingOptionss( getValueMappings( method ) )
            .setEnumMappingOptions( enumMappingOptions )
            .setSubclassMappings( subclassMappingOptions )
            .setSubclassValidator( subclassValidator )
            .setTypeUtils( typeUtils )
            .setTypeFactory( typeFactory )
            .setPrototypeMethods( prototypeMethods )
            .setContextProvidedMethods( contextProvidedMethods )
            .setVerboseLogging( options.isVerbose() )
            .build();
    }

    private ParameterProvidedMethods retrieveContextProvidedMethods(
            List<Parameter> contextParameters, TypeElement mapperToImplement, MapperOptions mapperConfig) {

        ParameterProvidedMethods.Builder builder = ParameterProvidedMethods.builder();
        for ( Parameter contextParam : contextParameters ) {
            if ( contextParam.getType().isPrimitive() || contextParam.getType().isArrayType() ) {
                continue;
            }
            List<SourceMethod> contextParamMethods = retrieveMethods(
                contextParam.getType(),
                mapperToImplement,
                mapperConfig,
                Collections.emptyList() );

            List<SourceMethod> contextProvidedMethods = new ArrayList<>( contextParamMethods.size() );
            for ( SourceMethod sourceMethod : contextParamMethods ) {
                if ( sourceMethod.isLifecycleCallbackMethod() || sourceMethod.isObjectFactory()
                    || sourceMethod.getConditionOptions().isAnyStrategyApplicable() ) {
                    contextProvidedMethods.add( sourceMethod );
                }
            }

            builder.addMethodsForParameter( contextParam, contextProvidedMethods );
        }

        return builder.build();
    }

    private SourceMethod getReferencedMethod(TypeElement usedMapper, ExecutableType methodType,
                                             ExecutableElement method, TypeElement mapperToImplement,
                                             List<Parameter> parameters) {
        Type returnType = typeFactory.getReturnType( methodType );
        List<Type> exceptionTypes = typeFactory.getThrownTypes( methodType );
        Type usedMapperAsType = typeFactory.getType( usedMapper );
        Type mapperToImplementAsType = typeFactory.getType( mapperToImplement );

        if ( !mapperToImplementAsType.canAccess( usedMapperAsType, method ) ) {
            return null;
        }


        Type definingType = typeFactory.getType( method.getEnclosingElement().asType() );

        return new SourceMethod.Builder()
            .setDeclaringMapper( usedMapper.equals( mapperToImplement ) ? null : usedMapperAsType )
            .setDefiningType( definingType )
            .setExecutable( method )
            .setParameters( parameters )
            .setReturnType( returnType )
            .setExceptionTypes( exceptionTypes )
            .setTypeUtils( typeUtils )
            .setTypeFactory( typeFactory )
            .setConditionOptions( getConditionOptions( method, parameters ) )
            .setVerboseLogging( options.isVerbose() )
            .build();
    }

    private boolean isValidLifecycleCallbackMethod(ExecutableElement method) {
        return Executables.isLifecycleCallbackMethod( method );
    }

    private boolean isValidReferencedMethod(List<Parameter> parameters) {
        return isValidReferencedOrFactoryMethod( 1, 1, parameters );
    }

    private boolean isValidFactoryMethod(ExecutableElement method, List<Parameter> parameters, Type returnType) {
        return !isVoid( returnType )
            && ( isValidReferencedOrFactoryMethod( 0, 0, parameters ) || hasFactoryAnnotation( method ) );
    }

    private boolean hasFactoryAnnotation(ExecutableElement method) {
        return ObjectFactoryGem.instanceOn( method ) != null;
    }

    private boolean isValidPresenceCheckMethod(ExecutableElement method, List<Parameter> parameters, Type returnType) {
        for ( Parameter param : parameters ) {

            if ( param.isSourcePropertyName() && !param.getType().isString() ) {
                messager.printMessage(
                    param.getElement(),
                    SourcePropertyNameGem.instanceOn( param.getElement() ).mirror(),
                    Message.RETRIEVAL_SOURCE_PROPERTY_NAME_WRONG_TYPE
                );
                return false;
            }

            if ( param.isTargetPropertyName() && !param.getType().isString() ) {
                messager.printMessage(
                    param.getElement(),
                    TargetPropertyNameGem.instanceOn( param.getElement() ).mirror(),
                    Message.RETRIEVAL_TARGET_PROPERTY_NAME_WRONG_TYPE
                );
                return false;
            }
        }
        return isBoolean( returnType ) && hasConditionAnnotation( method );
    }

    private boolean hasConditionAnnotation(ExecutableElement method) {
        return ConditionGem.instanceOn( method ) != null;
    }

    private boolean isVoid(Type returnType) {
        return returnType.getTypeMirror().getKind() == TypeKind.VOID;
    }

    private boolean isBoolean(Type returnType) {
        return Boolean.class.getCanonicalName().equals( returnType.getBoxedEquivalent().getFullyQualifiedName() );
    }

    private boolean isValidReferencedOrFactoryMethod(int sourceParamCount, int targetParamCount,
                                                     List<Parameter> parameters) {
        int validSourceParameters = 0;
        int targetParameters = 0;
        int targetTypeParameters = 0;

        for ( Parameter param : parameters ) {
            if ( param.isMappingTarget() ) {
                targetParameters++;
            }
            else if ( param.isTargetType() ) {
                targetTypeParameters++;
            }
            else if ( !param.isMappingContext() ) {
                validSourceParameters++;
            }
        }

        return validSourceParameters == sourceParamCount
            && targetParameters <= targetParamCount
            && targetTypeParameters <= 1;
    }

    private Parameter extractTargetParameter(List<Parameter> parameters) {
        for ( Parameter param : parameters ) {
            if ( param.isMappingTarget() ) {
                return param;
            }
        }

        return null;
    }

    private Type selectResultType(Type returnType, Parameter targetParameter) {
        if ( null != targetParameter ) {
            return targetParameter.getType();
        }
        else {
            return returnType;
        }
    }

    private boolean checkParameterAndReturnType(ExecutableElement method, List<Parameter> sourceParameters,
                                                Parameter targetParameter, List<Parameter> contextParameters,
                                                Type resultType, Type returnType, boolean containsTargetTypeParameter) {
        if ( sourceParameters.isEmpty() ) {
            messager.printMessage( method, Message.RETRIEVAL_NO_INPUT_ARGS );
            return false;
        }

        if ( targetParameter != null
            && ( sourceParameters.size() + contextParameters.size() + 1 != method.getParameters().size() ) ) {
            messager.printMessage( method, Message.RETRIEVAL_DUPLICATE_MAPPING_TARGETS );
            return false;
        }

        if ( isVoid( resultType ) ) {
            messager.printMessage( method, Message.RETRIEVAL_VOID_MAPPING_METHOD );
            return false;
        }

        if ( returnType.getTypeMirror().getKind() != TypeKind.VOID &&
                        !resultType.isAssignableTo( returnType ) &&
                        !resultType.isAssignableTo( typeFactory.effectiveResultTypeFor( returnType, null ) ) ) {
            messager.printMessage( method, Message.RETRIEVAL_NON_ASSIGNABLE_RESULTTYPE );
            return false;
        }

        for ( Parameter sourceParameter : sourceParameters ) {
            if ( sourceParameter.getType().isTypeVar() ) {
                messager.printMessage( method, Message.RETRIEVAL_TYPE_VAR_SOURCE );
                return false;
            }
        }

        Set<Type> contextParameterTypes = new HashSet<>();
        for ( Parameter contextParameter : contextParameters ) {
            if ( !contextParameterTypes.add( contextParameter.getType() ) ) {
                messager.printMessage( method, Message.RETRIEVAL_CONTEXT_PARAMS_WITH_SAME_TYPE );
                return false;
            }
        }

        if ( returnType.isTypeVar() || resultType.isTypeVar() ) {
                messager.printMessage( method, Message.RETRIEVAL_TYPE_VAR_RESULT );
                return false;
        }

        if ( sourceParameters.size() == 1 ) {
            Type parameterType = sourceParameters.get( 0 ).getType();

            if ( isStreamTypeOrIterableFromJavaStdLib( parameterType ) && !resultType.isIterableOrStreamType() ) {
                messager.printMessage( method, Message.RETRIEVAL_ITERABLE_TO_NON_ITERABLE );
                return false;
            }

            if ( !parameterType.isIterableOrStreamType() && isStreamTypeOrIterableFromJavaStdLib( resultType ) ) {
                messager.printMessage( method, Message.RETRIEVAL_NON_ITERABLE_TO_ITERABLE );
                return false;
            }

            if ( !parameterType.isIterableOrStreamType() && resultType.isArrayType() ) {
                messager.printMessage( method, Message.RETRIEVAL_NON_ITERABLE_TO_ARRAY );
                return false;
            }

            if ( parameterType.isPrimitive() ) {
                messager.printMessage( method, Message.RETRIEVAL_PRIMITIVE_PARAMETER );
                return false;
            }

            for ( Type typeParameter : parameterType.getTypeParameters() ) {
                if ( typeParameter.hasSuperBound() ) {
                    messager.printMessage( method, Message.RETRIEVAL_WILDCARD_SUPER_BOUND_SOURCE );
                    return false;
                }

                if ( typeParameter.isTypeVar() ) {
                    messager.printMessage( method, Message.RETRIEVAL_TYPE_VAR_SOURCE );
                    return false;
                }
            }
        }

        if ( containsTargetTypeParameter ) {
            messager.printMessage( method, Message.RETRIEVAL_MAPPING_HAS_TARGET_TYPE_PARAMETER );
            return false;
        }

        if ( resultType.isPrimitive() ) {
            messager.printMessage( method, Message.RETRIEVAL_PRIMITIVE_RETURN );
            return false;
        }

        for ( Type typeParameter : resultType.getTypeParameters() ) {
            if ( typeParameter.isTypeVar() ) {
                messager.printMessage( method, Message.RETRIEVAL_TYPE_VAR_RESULT );
                return false;
            }
            if ( typeParameter.hasExtendsBound() ) {
                messager.printMessage( method, Message.RETRIEVAL_WILDCARD_EXTENDS_BOUND_RESULT );
                return false;
            }
        }

        if ( Executables.isAfterMappingMethod( method ) ) {
            messager.printMessage( method, Message.RETRIEVAL_AFTER_METHOD_NOT_IMPLEMENTED );
            return false;
        }

        if ( Executables.isBeforeMappingMethod( method ) ) {
            messager.printMessage( method, Message.RETRIEVAL_BEFORE_METHOD_NOT_IMPLEMENTED );
            return false;
        }

        return true;
    }

    private boolean isStreamTypeOrIterableFromJavaStdLib(Type type) {
        return type.isStreamType() || ( type.isIterableType() && type.isJavaLangType() );
    }

    /**
     * Retrieves the mappings configured via {@code @Mapping} from the given method.
     *
     * @param method The method of interest
     * @param beanMapping options coming from bean mapping method
     * @return The mappings for the given method, keyed by target property name
     */
    private Set<MappingOptions> getMappings(ExecutableElement method, BeanMappingOptions beanMapping) {
        Set<MappingOptions> processedAnnotations = new RepeatableMappings( beanMapping )
                .getProcessedAnnotations( method );
        processedAnnotations.addAll( new IgnoredConditions( processedAnnotations )
                .getProcessedAnnotations( method ) );
        return processedAnnotations;
    }

    /**
     * Retrieves the subclass mappings configured via {@code @SubclassMapping} from the given method.
     *
     * @param method The method of interest
     * @param beanMapping options coming from bean mapping method
     *
     * @return The subclass mappings for the given method
     */
    private Set<SubclassMappingOptions> getSubclassMappings(List<Parameter> sourceParameters, Type resultType,
                                                            ExecutableElement method, BeanMappingOptions beanMapping,
                                                            SubclassValidator validator) {
        return new RepeatableSubclassMappings( beanMapping, sourceParameters, resultType, validator )
                        .getProcessedAnnotations( method );
    }

    /**
     * Retrieves the conditions configured via {@code @Condition} from the given method.
     *
     * @param method     The method of interest
     * @param parameters
     * @return The condition options for the given method
     */

    private Set<ConditionOptions> getConditionOptions(ExecutableElement method, List<Parameter> parameters) {
        return new MetaConditions( parameters ).getProcessedAnnotations( method );
    }

    private class RepeatableMappings extends RepeatableAnnotations<MappingGem, MappingsGem, MappingOptions> {
        private BeanMappingOptions beanMappingOptions;

        RepeatableMappings(BeanMappingOptions beanMappingOptions) {
            super( elementUtils, MAPPING_FQN, MAPPINGS_FQN );
            this.beanMappingOptions = beanMappingOptions;
        }

        @Override
        protected MappingGem singularInstanceOn(Element element) {
            return MappingGem.instanceOn( element );
        }

        @Override
        protected MappingsGem multipleInstanceOn(Element element) {
            return MappingsGem.instanceOn( element );
        }

        @Override
        protected void addInstance(MappingGem gem, Element method, Set<MappingOptions> mappings) {
            MappingOptions.addInstance(
                                       gem,
                                       (ExecutableElement) method,
                                       beanMappingOptions,
                                       messager,
                                       typeUtils,
                                       mappings );
        }

        @Override
        protected void addInstances(MappingsGem gem, Element method, Set<MappingOptions> mappings) {
            MappingOptions.addInstances(
                                        gem,
                                        (ExecutableElement) method,
                                        beanMappingOptions,
                                        messager,
                                        typeUtils,
                                        mappings );
        }
    }

    private class RepeatableSubclassMappings
        extends RepeatableAnnotations<SubclassMappingGem, SubclassMappingsGem, SubclassMappingOptions> {
        private final List<Parameter> sourceParameters;
        private final Type resultType;
        private SubclassValidator validator;
        private BeanMappingOptions beanMappingOptions;

        RepeatableSubclassMappings(BeanMappingOptions beanMappingOptions, List<Parameter> sourceParameters,
                                   Type resultType, SubclassValidator validator) {
            super( elementUtils, SUB_CLASS_MAPPING_FQN, SUB_CLASS_MAPPINGS_FQN );
            this.beanMappingOptions = beanMappingOptions;
            this.sourceParameters = sourceParameters;
            this.resultType = resultType;
            this.validator = validator;
        }

        @Override
        protected SubclassMappingGem singularInstanceOn(Element element) {
            return SubclassMappingGem.instanceOn( element );
        }

        @Override
        protected SubclassMappingsGem multipleInstanceOn(Element element) {
            return SubclassMappingsGem.instanceOn( element );
        }

        @Override
        protected void addInstance(SubclassMappingGem gem,
                                   Element method,
                                   Set<SubclassMappingOptions> mappings) {
            SubclassMappingOptions.addInstance(
                                               gem,
                                               (ExecutableElement) method,
                                               beanMappingOptions,
                                               messager,
                                               typeUtils,
                                               mappings,
                                               sourceParameters,
                                               resultType,
                                               validator );
        }

        @Override
        protected void addInstances(SubclassMappingsGem gem,
                                    Element method,
                                    Set<SubclassMappingOptions> mappings) {
            SubclassMappingOptions.addInstances(
                                                gem,
                                                (ExecutableElement) method,
                                                beanMappingOptions,
                                                messager,
                                                typeUtils,
                                                mappings,
                                                sourceParameters,
                                                resultType,
                                                validator );
        }
    }

    /**
     * Retrieves the mappings configured via {@code @ValueMapping} from the given
     * method.
     *
     * @param method The method of interest
     *
     * @return The mappings for the given method, keyed by target property name
     */
    private List<ValueMappingOptions> getValueMappings(ExecutableElement method) {
        Set<ValueMappingOptions> processedAnnotations = new RepeatValueMappings().getProcessedAnnotations( method );
        return new ArrayList<>(processedAnnotations);
    }

    private class RepeatValueMappings
        extends RepeatableAnnotations<ValueMappingGem, ValueMappingsGem, ValueMappingOptions> {

        protected RepeatValueMappings() {
            super( elementUtils, VALUE_MAPPING_FQN, VALUE_MAPPINGS_FQN );
        }

        @Override
        protected ValueMappingGem singularInstanceOn(Element element) {
            return ValueMappingGem.instanceOn( element );
        }

        @Override
        protected ValueMappingsGem multipleInstanceOn(Element element) {
            return ValueMappingsGem.instanceOn( element );
        }

        @Override
        protected void addInstance(ValueMappingGem gem, Element source, Set<ValueMappingOptions> mappings) {
            ValueMappingOptions valueMappingOptions = ValueMappingOptions.fromMappingGem( gem );
            mappings.add( valueMappingOptions );
        }

        @Override
        protected void addInstances(ValueMappingsGem gems, Element source, Set<ValueMappingOptions> mappings) {
            ValueMappingOptions.fromMappingsGem( gems, (ExecutableElement) source, messager, mappings );
        }
    }

    private class MetaConditions extends MetaAnnotations<ConditionGem, ConditionOptions> {

        protected final List<Parameter> parameters;

        protected MetaConditions(List<Parameter> parameters) {
            super( elementUtils, CONDITION_FQN );
            this.parameters = parameters;
        }

        @Override
        protected ConditionGem instanceOn(Element element) {
            return ConditionGem.instanceOn( element );
        }

        @Override
        protected void addInstance(ConditionGem gem, Element source, Set<ConditionOptions> values) {
            ConditionOptions options = ConditionOptions.getInstanceOn(
                gem,
                (ExecutableElement) source,
                parameters,
                messager
            );
            if ( options != null ) {
                values.add( options );
            }
        }
    }

    private class IgnoredConditions extends RepeatableAnnotations<IgnoredGem, IgnoredListGem, MappingOptions> {

        protected final Set<MappingOptions> processedAnnotations;

        protected IgnoredConditions( Set<MappingOptions> processedAnnotations ) {
            super( elementUtils, IGNORED_FQN, IGNORED_LIST_FQN );
            this.processedAnnotations = processedAnnotations;
        }

        @Override
        protected IgnoredGem singularInstanceOn(Element element) {
            return IgnoredGem.instanceOn( element );
        }

        @Override
        protected IgnoredListGem multipleInstanceOn(Element element) {
            return IgnoredListGem.instanceOn( element );
        }

        @Override
        protected void addInstance(IgnoredGem gem, Element method, Set<MappingOptions> mappings) {
            IgnoredGem ignoredGem = IgnoredGem.instanceOn( method );
            if ( ignoredGem == null ) {
                ignoredGem = gem;
            }
            String prefix = ignoredGem.prefix().get();
            for ( String target : ignoredGem.targets().get() ) {
                String realTarget = target;
                if ( !prefix.isEmpty() ) {
                    realTarget = prefix + "." + target;
                }
                MappingOptions mappingOptions = MappingOptions.forIgnore( realTarget );
                if ( processedAnnotations.contains( mappingOptions ) || mappings.contains( mappingOptions ) ) {
                    messager.printMessage( method, Message.PROPERTYMAPPING_DUPLICATE_TARGETS, realTarget );
                }
                else {
                    mappings.add( mappingOptions );
                }
            }
        }

        @Override
        protected void addInstances(IgnoredListGem gem, Element method, Set<MappingOptions> mappings) {
            IgnoredListGem ignoredListGem = IgnoredListGem.instanceOn( method );
            for ( IgnoredGem ignoredGem : ignoredListGem.value().get() ) {
                addInstance( ignoredGem, method, mappings );
            }
        }
    }

}
